package br.com.nonna_back.mappers;

import br.com.nonna_back.dtos.PedidoDto;
import br.com.nonna_back.dtos.PedidoResponseDto;
import br.com.nonna_back.entities.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    public Pedido toEntity(PedidoDto dto) {
        Pedido pedido = new Pedido();
        pedido.setTipoEntrega(dto.getTipoEntrega());
        pedido.setEndereco(dto.getEndereco());
        pedido.setFormaPagamento(dto.getFormaPagamento());
        pedido.setTelefone(dto.getTelefone());
        return pedido;
    }

    public PedidoResponseDto toResponseDto(Pedido pedido) {
        PedidoResponseDto dto = new PedidoResponseDto();
        dto.setId(pedido.getId());
        dto.setIdUsuario(pedido.getIdUsuario());
        dto.setPrecoTotal(pedido.getPrecoTotal());
        dto.setTipoEntrega(pedido.getTipoEntrega());
        dto.setEndereco(pedido.getEndereco());
        dto.setFormaPagamento(pedido.getFormaPagamento());
        dto.setTelefone(pedido.getTelefone());
        dto.setHorarioCriacao(pedido.getHorarioCriacao());
        dto.setHorarioSaida(pedido.getHorarioSaida());
        dto.setHorarioFinalizacao(pedido.getHorarioFinalizacao());
        dto.setStatus(pedido.getStatus());
        dto.setMotivoCancelamento(pedido.getMotivoCancelamento());
        return dto;
    }
}
