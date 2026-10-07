package br.com.nonna_back.mappers;

import br.com.nonna_back.dtos.ReservaDto;
import br.com.nonna_back.dtos.ReservaResponseDto;
import br.com.nonna_back.entities.Reserva;
import org.springframework.stereotype.Component;

@Component
public class ReservaMapper {

    public Reserva toEntity(ReservaDto dto) {
        Reserva reserva = new Reserva();
        reserva.setNome(dto.getNome());
        reserva.setEmail(dto.getEmail());
        reserva.setTelefone(dto.getTelefone());
        reserva.setDataHora(dto.getDataHora());
        reserva.setQuantidadePessoas(dto.getQuantidadePessoas());
        reserva.setAreaPreferida(dto.getAreaPreferida());
        reserva.setPrecisaCadeirao(dto.isPrecisaCadeirao());
        reserva.setComemoracaoAniversario(dto.isComemoracaoAniversario());
        reserva.setPrecisaAcessibilidade(dto.isPrecisaAcessibilidade());
        reserva.setObservacoes(dto.getObservacoes());
        return reserva;
    }

    public ReservaResponseDto toResponseDto(Reserva reserva) {
        ReservaResponseDto dto = new ReservaResponseDto();
        dto.setId(reserva.getId());
        dto.setNome(reserva.getNome());
        dto.setEmail(reserva.getEmail());
        dto.setTelefone(reserva.getTelefone());
        dto.setDataHora(reserva.getDataHora());
        dto.setQuantidadePessoas(reserva.getQuantidadePessoas());
        dto.setAreaPreferida(reserva.getAreaPreferida());
        dto.setPrecisaCadeirao(reserva.isPrecisaCadeirao());
        dto.setComemoracaoAniversario(reserva.isComemoracaoAniversario());
        dto.setPrecisaAcessibilidade(reserva.isPrecisaAcessibilidade());
        dto.setObservacoes(reserva.getObservacoes());
        dto.setStatus(reserva.getStatus());
        dto.setMotivoCancelamento(reserva.getMotivoCancelamento());
        return dto;
    }
}
