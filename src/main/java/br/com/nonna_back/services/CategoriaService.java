package br.com.nonna_back.services;

import br.com.nonna_back.dtos.PageDto;
import br.com.nonna_back.entities.Categoria;
import br.com.nonna_back.exceptions.RecursoNaoEncontradoException;
import br.com.nonna_back.repositories.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CategoriaService {
    private final CategoriaRepository repository;
    public CategoriaService(CategoriaRepository repository) { this.repository = repository; }

    @Transactional
    public Categoria criar(Categoria categoria) {
        categoria.setId(UUID.randomUUID().toString());
        repository.salvar(categoria);
        return categoria;
    }

    // Padrao da aula: pagina default = 30, maximo = 100. Nunca confiamos
    // no "size" que vem da query string sem checar os limites.
    public PageDto<Categoria> listar(int page, int size) {
        if (size > 100) size = 100;
        if (size < 1) size = 30;
        return new PageDto<>(repository.buscarTodos(size, page * size), page, size, repository.contarTodos());
    }

    public Categoria buscarPorId(String id) {
        return repository.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("CATEGORIA NÃO ENCONTRADA"));
    }

    @Transactional
    public Categoria atualizar(String id, Categoria categoria) {
        Categoria existente = buscarPorId(id);
        existente.setNome(categoria.getNome());
        repository.atualizar(existente);
        return existente;
    }

    @Transactional
    public void remover(String id) {
        buscarPorId(id);
        repository.deletar(id);
    }
}
