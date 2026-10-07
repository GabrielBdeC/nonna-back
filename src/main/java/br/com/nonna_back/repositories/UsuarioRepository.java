package br.com.nonna_back.repositories;

import br.com.nonna_back.entities.Usuario;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {
    private final JdbcTemplate jdbc;
    public UsuarioRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public void salvar(Usuario usuario) {
        jdbc.update("INSERT INTO usuario (id, nome, email, senha, telefone, tipo) VALUES (?, ?, ?, ?, ?, ?)",
                usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getTelefone(), usuario.getTipo());
    }

    public List<Usuario> buscarTodos(int limit, int offset) {
        return jdbc.query("SELECT * FROM usuario ORDER BY nome LIMIT ? OFFSET ?", new BeanPropertyRowMapper<>(Usuario.class), limit, offset);
    }

    public long contarTodos() {
        Long count = jdbc.queryForObject("SELECT COUNT(*) FROM usuario", Long.class);
        return count != null ? count : 0;
    }

    public Optional<Usuario> buscarPorId(String id) {
        List<Usuario> lista = jdbc.query("SELECT * FROM usuario WHERE id = ?", new BeanPropertyRowMapper<>(Usuario.class), id);
        return lista.isEmpty() ? Optional.empty() : Optional.of(lista.get(0));
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        List<Usuario> lista = jdbc.query("SELECT * FROM usuario WHERE email = ?", new BeanPropertyRowMapper<>(Usuario.class), email);
        return lista.isEmpty() ? Optional.empty() : Optional.of(lista.get(0));
    }

    public void atualizar(Usuario usuario) {
        jdbc.update("UPDATE usuario SET nome=?, email=?, senha=?, telefone=?, tipo=? WHERE id=?",
                usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getTelefone(), usuario.getTipo(), usuario.getId());
    }
}
