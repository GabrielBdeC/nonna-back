package br.com.nonna_back.repositories;

import br.com.nonna_back.entities.Pedido;
import br.com.nonna_back.entities.ProdutoPedido;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PedidoRepository {
    private final JdbcTemplate jdbc;
    public PedidoRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public void salvar(Pedido pedido) {
        jdbc.update("INSERT INTO pedido (id, id_usuario, preco_total, tipo_entrega, endereco, forma_pagamento, telefone, " +
                        "horario_criacao, horario_saida, horario_finalizacao, status, motivo_cancelamento) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                pedido.getId(), pedido.getIdUsuario(), pedido.getPrecoTotal(), pedido.getTipoEntrega(), pedido.getEndereco(),
                pedido.getFormaPagamento(), pedido.getTelefone(), pedido.getHorarioCriacao(), pedido.getHorarioSaida(),
                pedido.getHorarioFinalizacao(), pedido.getStatus(), pedido.getMotivoCancelamento());
    }

    public void salvarItem(ProdutoPedido item) {
        jdbc.update("INSERT INTO produto_pedido (id, id_pedido, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?, ?)",
                item.getId(), item.getIdPedido(), item.getIdProduto(), item.getQuantidade(), item.getPrecoUnitario());
    }

    public List<ProdutoPedido> buscarItensPorPedido(String idPedido) {
        return jdbc.query("SELECT * FROM produto_pedido WHERE id_pedido = ?", new BeanPropertyRowMapper<>(ProdutoPedido.class), idPedido);
    }

    // Pauta da aula de paginacao: aqui a ordenacao (mais atrasado primeiro)
    // acontece no banco, antes do LIMIT/OFFSET, senao a pagina 2 poderia
    // repetir ou saltar pedidos.
    public List<Pedido> buscarNaoConcluidos(int limit, int offset) {
        return jdbc.query("SELECT * FROM pedido WHERE status NOT IN ('CONCLUIDO', 'CANCELADO') " +
                "ORDER BY horario_criacao ASC LIMIT ? OFFSET ?", new BeanPropertyRowMapper<>(Pedido.class), limit, offset);
    }

    public long contarNaoConcluidos() {
        Long count = jdbc.queryForObject("SELECT COUNT(*) FROM pedido WHERE status NOT IN ('CONCLUIDO', 'CANCELADO')", Long.class);
        return count != null ? count : 0;
    }

    public Optional<Pedido> buscarPorId(String id) {
        List<Pedido> lista = jdbc.query("SELECT * FROM pedido WHERE id = ?", new BeanPropertyRowMapper<>(Pedido.class), id);
        return lista.isEmpty() ? Optional.empty() : Optional.of(lista.get(0));
    }

    public void atualizar(Pedido pedido) {
        jdbc.update("UPDATE pedido SET status=?, horario_saida=?, horario_finalizacao=?, motivo_cancelamento=? WHERE id=?",
                pedido.getStatus(), pedido.getHorarioSaida(), pedido.getHorarioFinalizacao(), pedido.getMotivoCancelamento(), pedido.getId());
    }
}
