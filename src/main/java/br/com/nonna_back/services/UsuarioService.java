package br.com.nonna_back.services;

import br.com.nonna_back.dtos.PageDto;
import br.com.nonna_back.dtos.UsuarioAtualizacaoDto;
import br.com.nonna_back.entities.Usuario;
import br.com.nonna_back.exceptions.RecursoNaoEncontradoException;
import br.com.nonna_back.exceptions.RegraNegocioException;
import br.com.nonna_back.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Usuario criar(Usuario usuario) {
        if (repository.buscarPorEmail(usuario.getEmail()).isPresent()) {
            throw new RegraNegocioException("E-MAIL JÁ CADASTRADO");
        }
        usuario.setId(UUID.randomUUID().toString());
        // Nunca guardamos a senha como veio do formulario: so o hash.
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
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
    public Usuario atualizar(String id, UsuarioAtualizacaoDto dto) {
        Usuario existente = buscarPorId(id);
        repository.buscarPorEmail(dto.getEmail()).ifPresent(outro -> {
            if (!outro.getId().equals(id)) throw new RegraNegocioException("E-MAIL JÁ CADASTRADO");
        });
        existente.setNome(dto.getNome());
        existente.setEmail(dto.getEmail());
        existente.setTelefone(dto.getTelefone());
        existente.setTipo(dto.getTipo());
        // Senha em branco = "nao quero trocar a senha". Quando vem
        // preenchida, exige a senha atual batendo -- so o token nao basta.
        if (dto.getSenha() != null && !dto.getSenha().isBlank()) {
            if (dto.getSenhaAtual() == null || !passwordEncoder.matches(dto.getSenhaAtual(), existente.getSenha())) {
                throw new RegraNegocioException("SENHA ATUAL INCORRETA");
            }
            existente.setSenha(passwordEncoder.encode(dto.getSenha()));
        }
        repository.atualizar(existente);
        return existente;
    }
}
