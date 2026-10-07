package br.com.nonna_back.services;

import br.com.nonna_back.dtos.PageDto;
import br.com.nonna_back.entities.Pedido;
import br.com.nonna_back.entities.Produto;
import br.com.nonna_back.entities.ProdutoPedido;
import br.com.nonna_back.exceptions.RecursoNaoEncontradoException;
import br.com.nonna_back.exceptions.RegraNegocioException;
import br.com.nonna_back.repositories.PedidoRepository;
import br.com.nonna_back.repositories.ProdutoRepository;
import br.com.nonna_back.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {
    private final PedidoRepository repository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public PedidoService(PedidoRepository repository, ProdutoRepository produtoRepository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.produtoRepository = produtoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Pedido criar(Pedido pedido, List<ProdutoPedido> itensSolicitados) {
        usuarioRepository.buscarPorId(pedido.getIdUsuario()).orElseThrow(() -> new RecursoNaoEncontradoException("USUÁRIO NÃO ENCONTRADO"));
        if ("DELIVERY".equals(pedido.getTipoEntrega()) && (pedido.getEndereco() == null || pedido.getEndereco().isBlank())) {
            throw new RegraNegocioException("ENDEREÇO É OBRIGATÓRIO PARA ENTREGA");
        }

        pedido.setId(UUID.randomUUID().toString());
        pedido.setHorarioCriacao(LocalDateTime.now());
        pedido.setStatus("CRIADO");

        // O preco de cada item e sempre lido do produto no banco, nunca do
        // que o cliente mandou: assim ele nao consegue "pedir" um preco menor.
        BigDecimal precoTotal = BigDecimal.ZERO;
        for (ProdutoPedido item : itensSolicitados) {
            Produto produto = produtoRepository.buscarPorId(item.getIdProduto())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("PRODUTO NÃO ENCONTRADO"));
            item.setId(UUID.randomUUID().toString());
            item.setPrecoUnitario(produto.getPreco());
            precoTotal = precoTotal.add(produto.getPreco().multiply(BigDecimal.valueOf(item.getQuantidade())));
        }
        pedido.setPrecoTotal(precoTotal);

        repository.salvar(pedido);
        for (ProdutoPedido item : itensSolicitados) {
            item.setIdPedido(pedido.getId());
            repository.salvarItem(item);
        }
        return pedido;
    }

    // Lista apenas pedidos nao concluidos/cancelados, do mais atrasado para
    // o mais recente, e paginada (default 30, maximo 100).
    public PageDto<Pedido> listarNaoConcluidos(int page, int size) {
        if (size > 100) size = 100;
        if (size < 1) size = 30;
        return new PageDto<>(repository.buscarNaoConcluidos(size, page * size), page, size, repository.contarNaoConcluidos());
    }

    public Pedido buscarPorId(String id) {
        return repository.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("PEDIDO NÃO ENCONTRADO"));
    }

    public List<ProdutoPedido> buscarItens(String idPedido) {
        return repository.buscarItensPorPedido(idPedido);
    }

    @Transactional
    public Pedido atualizar(String id, String status, LocalDateTime horarioSaida, LocalDateTime horarioFinalizacao) {
        Pedido existente = buscarPorId(id);
        existente.setStatus(status);
        if (horarioSaida != null) existente.setHorarioSaida(horarioSaida);
        if (horarioFinalizacao != null) existente.setHorarioFinalizacao(horarioFinalizacao);
        repository.atualizar(existente);
        return existente;
    }

    @Transactional
    public Pedido cancelar(String id, String motivo) {
        Pedido existente = buscarPorId(id);
        if ("CONCLUIDO".equals(existente.getStatus())) {
            throw new RegraNegocioException("PEDIDO JÁ CONCLUÍDO NÃO PODE SER CANCELADO");
        }
        existente.setStatus("CANCELADO");
        existente.setMotivoCancelamento(motivo);
        repository.atualizar(existente);
        return existente;
    }
}
