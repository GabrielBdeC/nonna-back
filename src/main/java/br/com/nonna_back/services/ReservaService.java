package br.com.nonna_back.services;

import br.com.nonna_back.dtos.PageDto;
import br.com.nonna_back.entities.Reserva;
import br.com.nonna_back.exceptions.RecursoNaoEncontradoException;
import br.com.nonna_back.exceptions.RegraNegocioException;
import br.com.nonna_back.repositories.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ReservaService {
    private final ReservaRepository repository;
    public ReservaService(ReservaRepository repository) { this.repository = repository; }

    @Transactional
    public Reserva criar(Reserva reserva) {
        reserva.setId(UUID.randomUUID().toString());
        reserva.setStatus("ATIVA");
        repository.salvar(reserva);
        return reserva;
    }

    public PageDto<Reserva> listar(int page, int size) {
        if (size > 100) size = 100;
        if (size < 1) size = 30;
        return new PageDto<>(repository.buscarTodos(size, page * size), page, size, repository.contarTodos());
    }

    public Reserva buscarPorId(String id) {
        return repository.buscarPorId(id).orElseThrow(() -> new RecursoNaoEncontradoException("RESERVA NÃO ENCONTRADA"));
    }

    @Transactional
    public Reserva cancelar(String id, String motivo) {
        Reserva reserva = buscarPorId(id);
        if ("CANCELADA".equals(reserva.getStatus())) {
            throw new RegraNegocioException("RESERVA JÁ CANCELADA");
        }
        reserva.setStatus("CANCELADA");
        reserva.setMotivoCancelamento(motivo);
        repository.atualizar(reserva);
        return reserva;
    }
}
