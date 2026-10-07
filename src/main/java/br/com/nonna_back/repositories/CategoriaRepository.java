package br.com.nonna_back.repositories;

import br.com.nonna_back.entities.Categoria;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaRepository {
    private final JdbcTemplate jdbc;
    public CategoriaRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public void salvar(Categoria categoria) {
        jdbc.update("INSERT INTO categoria (id, nome) VALUES (?, ?)", categoria.getId(), categoria.getNome());
    }

    public List<Categoria> buscarTodos(int limit, int offset) {
        return jdbc.query("SELECT * FROM categoria ORDER BY nome LIMIT ? OFFSET ?", new BeanPropertyRowMapper<>(Categoria.class), limit, offset);
    }

    public long contarTodos() {
        Long count = jdbc.queryForObject("SELECT COUNT(*) FROM categoria", Long.class);
        return count != null ? count : 0;
    }

    public Optional<Categoria> buscarPorId(String id) {
        List<Categoria> lista = jdbc.query("SELECT * FROM categoria WHERE id = ?", new BeanPropertyRowMapper<>(Categoria.class), id);
        return lista.isEmpty() ? Optional.empty() : Optional.of(lista.get(0));
    }

    public void atualizar(Categoria categoria) {
        jdbc.update("UPDATE categoria SET nome = ? WHERE id = ?", categoria.getNome(), categoria.getId());
    }

    public void deletar(String id) {
        jdbc.update("DELETE FROM categoria WHERE id = ?", id);
    }
}
