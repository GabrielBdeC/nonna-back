package br.com.nonna_back.repositories;

import br.com.nonna_back.entities.Reserva;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReservaRepository {
    private final JdbcTemplate jdbc;
    public ReservaRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    public void salvar(Reserva reserva) {
        jdbc.update("INSERT INTO reserva (id, nome, email, telefone, data_hora, quantidade_pessoas, area_preferida, " +
                        "precisa_cadeirao, comemoracao_aniversario, precisa_acessibilidade, observacoes, status, motivo_cancelamento) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                reserva.getId(), reserva.getNome(), reserva.getEmail(), reserva.getTelefone(), reserva.getDataHora(),
                reserva.getQuantidadePessoas(), reserva.getAreaPreferida(), reserva.isPrecisaCadeirao(),
                reserva.isComemoracaoAniversario(), reserva.isPrecisaAcessibilidade(), reserva.getObservacoes(),
                reserva.getStatus(), reserva.getMotivoCancelamento());
    }

    public List<Reserva> buscarTodos(int limit, int offset) {
        return jdbc.query("SELECT * FROM reserva ORDER BY data_hora LIMIT ? OFFSET ?", new BeanPropertyRowMapper<>(Reserva.class), limit, offset);
    }

    public long contarTodos() {
        Long count = jdbc.queryForObject("SELECT COUNT(*) FROM reserva", Long.class);
        return count != null ? count : 0;
    }

    public Optional<Reserva> buscarPorId(String id) {
        List<Reserva> lista = jdbc.query("SELECT * FROM reserva WHERE id = ?", new BeanPropertyRowMapper<>(Reserva.class), id);
        return lista.isEmpty() ? Optional.empty() : Optional.of(lista.get(0));
    }

    public void atualizar(Reserva reserva) {
        jdbc.update("UPDATE reserva SET status=?, motivo_cancelamento=? WHERE id=?",
                reserva.getStatus(), reserva.getMotivoCancelamento(), reserva.getId());
    }
}
