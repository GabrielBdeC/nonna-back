package br.com.nonna_back.mappers;

import br.com.nonna_back.dtos.ProdutoDto;
import br.com.nonna_back.dtos.ProdutoResponseDto;
import br.com.nonna_back.entities.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

    public Produto toEntity(ProdutoDto dto) {
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setIdCategoria(dto.getIdCategoria());
        produto.setImagem(dto.getImagem());
        return produto;
    }

    public ProdutoResponseDto toResponseDto(Produto produto) {
        ProdutoResponseDto dto = new ProdutoResponseDto();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setIdCategoria(produto.getIdCategoria());
        dto.setImagem(produto.getImagem());
        return dto;
    }
}
