package br.com.nonna_back.services;

import br.com.nonna_back.dtos.PageDto;
import br.com.nonna_back.entities.Usuario;
import br.com.nonna_back.exceptions.RecursoNaoEncontradoException;
import br.com.nonna_back.exceptions.RegraNegocioException;
import br.com.nonna_back.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    public UsuarioService(UsuarioRepository repository) { this.repository = repository; }

    @Transactional
    public Usuario criar(Usuario usuario) {
        if (repository.buscarPorEmail(usuario.getEmail()).isPresent()) {
            throw new RegraNegocioException("E-MAIL JÁ CADASTRADO");
        }
        usuario.setId(UUID.randomUUID().toString());
        repository.salvar(usuario);
        return usuario;
    }

    public PageDto<Usuario> listar(int page, int size) {
        if (size > 100) size = 100;
        if (size < 1) size = 30;
        return new PageDto<>(repository.buscarTodos(size, page * size), page, size, repository.contarTodos());
    }

    public Usuario buscarPorId(String id) {
        return repository.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("USUÁRIO NÃO ENCONTRADO"));
    }

    @Transactional
    public Usuario atualizar(String id, Usuario usuario) {
        Usuario existente = buscarPorId(id);
        repository.buscarPorEmail(usuario.getEmail()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) throw new RegraNegocioException("E-MAIL JÁ CADASTRADO");
        });
        existente.setNome(usuario.getNome());
        existente.setEmail(usuario.getEmail());
        existente.setSenha(usuario.getSenha());
        existente.setTelefone(usuario.getTelefone());
        existente.setTipo(usuario.getTipo());
        repository.atualizar(existente);
        return existente;
    }
}
