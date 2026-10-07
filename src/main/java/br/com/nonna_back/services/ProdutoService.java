package br.com.nonna_back.services;

import br.com.nonna_back.dtos.PageDto;
import br.com.nonna_back.entities.Produto;
import br.com.nonna_back.exceptions.RecursoNaoEncontradoException;
import br.com.nonna_back.repositories.CategoriaRepository;
import br.com.nonna_back.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProdutoService {
    private final ProdutoRepository repository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional
    public Produto criar(Produto produto) {
        validarCategoria(produto.getIdCategoria());
        produto.setId(UUID.randomUUID().toString());
        repository.salvar(produto);
        return produto;
    }

    public PageDto<Produto> listar(int page, int size, String idCategoria) {
        if (size > 100) size = 100;
        if (size < 1) size = 30;
        if (idCategoria != null && !idCategoria.isBlank()) {
            return new PageDto<>(repository.buscarPorCategoria(idCategoria, size, page * size), page, size, repository.contarPorCategoria(idCategoria));
        }
        return new PageDto<>(repository.buscarTodos(size, page * size), page, size, repository.contarTodos());
    }

    public Produto buscarPorId(String id) {
        return repository.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("PRODUTO NÃO ENCONTRADO"));
    }

    @Transactional
    public Produto atualizar(String id, Produto produto) {
        Produto existente = buscarPorId(id);
        validarCategoria(produto.getIdCategoria());
        existente.setNome(produto.getNome());
        existente.setDescricao(produto.getDescricao());
        existente.setPreco(produto.getPreco());
        existente.setIdCategoria(produto.getIdCategoria());
        existente.setImagem(produto.getImagem());
        repository.atualizar(existente);
        return existente;
    }

    @Transactional
    public void remover(String id) {
        buscarPorId(id);
        repository.deletar(id);
    }

    private void validarCategoria(String idCategoria) {
        categoriaRepository.buscarPorId(idCategoria).orElseThrow(() -> new RecursoNaoEncontradoException("CATEGORIA NÃO ENCONTRADA"));
    }
}
