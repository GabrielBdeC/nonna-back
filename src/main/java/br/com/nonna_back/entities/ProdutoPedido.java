package br.com.nonna_back.entities;

import java.math.BigDecimal;

public class ProdutoPedido {
    private String id;
    private String idPedido;
    private String idProduto;
    private int quantidade;
    private BigDecimal precoUnitario; // preco do produto capturado no momento do pedido

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdPedido() { return idPedido; }
    public void setIdPedido(String idPedido) { this.idPedido = idPedido; }
    public String getIdProduto() { return idProduto; }
    public void setIdProduto(String idProduto) { this.idProduto = idProduto; }
    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
    public BigDecimal getPrecoUnitario() { return precoUnitario; }
    public void setPrecoUnitario(BigDecimal precoUnitario) { this.precoUnitario = precoUnitario; }
}
