package br.com.nonna_back.services;

import br.com.nonna_back.entities.Usuario;
import br.com.nonna_back.exceptions.CredenciaisInvalidasException;
import br.com.nonna_back.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = repository.buscarPorEmail(email)
                .orElseThrow(() -> new CredenciaisInvalidasException("E-MAIL OU SENHA INVÁLIDOS"));

        // matches() recalcula o hash do "senha" digitado usando o MESMO sal
        // que ja esta guardado em usuario.getSenha(), e compara os hashes.
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new CredenciaisInvalidasException("E-MAIL OU SENHA INVÁLIDOS");
        }
        return usuario;
    }
}
