package br.com.nonna_back.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ItemPedidoDto {
    @NotBlank(message = "O produto do item é obrigatório")
    private String idProduto;
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private int quantidade;

    public String getIdProduto() { return idProduto; }
    public void setIdProduto(String idProduto) { this.idProduto = idProduto; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
