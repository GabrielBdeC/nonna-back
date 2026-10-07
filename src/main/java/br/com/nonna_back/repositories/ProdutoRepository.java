package br.com.nonna_back.repositories;

import br.com.nonna_back.entities.Produto;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProdutoRepository {
    private final JdbcTemplate jdbc;
    public ProdutoRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public void salvar(Produto produto) {
        jdbc.update("INSERT INTO produto (id, nome, descricao, preco, id_categoria, imagem) VALUES (?, ?, ?, ?, ?, ?)",
                produto.getId(), produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getIdCategoria(), produto.getImagem());
    }

    public List<Produto> buscarTodos(int limit, int offset) {
        return jdbc.query("SELECT * FROM produto ORDER BY nome LIMIT ? OFFSET ?", new BeanPropertyRowMapper<>(Produto.class), limit, offset);
    }

    public List<Produto> buscarPorCategoria(String idCategoria, int limit, int offset) {
        return jdbc.query("SELECT * FROM produto WHERE id_categoria = ? ORDER BY nome LIMIT ? OFFSET ?",
                new BeanPropertyRowMapper<>(Produto.class), idCategoria, limit, offset);
    }

    public long contarTodos() {
        Long count = jdbc.queryForObject("SELECT COUNT(*) FROM produto", Long.class);
        return count != null ? count : 0;
    }

    public long contarPorCategoria(String idCategoria) {
        Long count = jdbc.queryForObject("SELECT COUNT(*) FROM produto WHERE id_categoria = ?", Long.class, idCategoria);
        return count != null ? count : 0;
    }

    public Optional<Produto> buscarPorId(String id) {
        List<Produto> lista = jdbc.query("SELECT * FROM produto WHERE id = ?", new BeanPropertyRowMapper<>(Produto.class), id);
        return lista.isEmpty() ? Optional.empty() : Optional.of(lista.get(0));
    }

    public void atualizar(Produto produto) {
        jdbc.update("UPDATE produto SET nome=?, descricao=?, preco=?, id_categoria=?, imagem=? WHERE id=?",
                produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getIdCategoria(), produto.getImagem(), produto.getId());
    }

    public void deletar(String id) {
        jdbc.update("DELETE FROM produto WHERE id = ?", id);
    }
}
