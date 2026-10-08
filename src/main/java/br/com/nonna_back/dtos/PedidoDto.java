package br.com.nonna_back.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class PedidoDto {
    // idUsuario NAO vem no corpo da requisicao: quem esta fazendo o pedido
    // e sempre o usuario do token JWT. O Controller preenche isso depois
    // de montar a Entity -- assim ninguem consegue fazer pedido em nome
    // de outra pessoa so alterando o JSON.
    @NotBlank(message = "O tipo de entrega é obrigatório")
    @Pattern(regexp = "RETIRADA|DELIVERY", message = "O tipo de entrega deve ser RETIRADA ou DELIVERY")
    private String tipoEntrega;
    private String endereco;
    @NotBlank(message = "A forma de pagamento é obrigatória")
    private String formaPagamento;
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;
    @NotEmpty(message = "O pedido deve ter ao menos um item")
    @Valid
    private List<ItemPedidoDto> itens;

    public String getTipoEntrega() { return tipoEntrega; }
    public void setTipoEntrega(String tipoEntrega) { this.tipoEntrega = tipoEntrega; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public List<ItemPedidoDto> getItens() { return itens; }
    public void setItens(List<ItemPedidoDto> itens) { this.itens = itens; }
}
