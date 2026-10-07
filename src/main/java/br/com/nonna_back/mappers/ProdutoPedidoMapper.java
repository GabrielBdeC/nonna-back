package br.com.nonna_back.mappers;

import br.com.nonna_back.dtos.ItemPedidoResponseDto;
import br.com.nonna_back.entities.ProdutoPedido;
import org.springframework.stereotype.Component;

@Component
public class ProdutoPedidoMapper {

    public ItemPedidoResponseDto toResponseDto(ProdutoPedido item) {
        ItemPedidoResponseDto dto = new ItemPedidoResponseDto();
        dto.setId(item.getId());
        dto.setIdProduto(item.getIdProduto());
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        dto.setSubtotal(item.getPrecoUnitario().multiply(java.math.BigDecimal.valueOf(item.getQuantidade())));
        return dto;
    }
}
