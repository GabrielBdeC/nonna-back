package br.com.nonna_back.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoResponseDto {
    private String id;
    private String idUsuario;
    private BigDecimal precoTotal;
    private String tipoEntrega;
    private String endereco;
    private String formaPagamento;
    private String telefone;
    private LocalDateTime horarioCriacao;
    private LocalDateTime horarioSaida;
    private LocalDateTime horarioFinalizacao;
    private String status;
    private String motivoCancelamento;
    private List<ItemPedidoResponseDto> itens;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    public BigDecimal getPrecoTotal() { return precoTotal; }
    public void setPrecoTotal(BigDecimal precoTotal) { this.precoTotal = precoTotal; }
    public String getTipoEntrega() { return tipoEntrega; }
    public void setTipoEntrega(String tipoEntrega) { this.tipoEntrega = tipoEntrega; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public LocalDateTime getHorarioCriacao() { return horarioCriacao; }
    public void setHorarioCriacao(LocalDateTime horarioCriacao) { this.horarioCriacao = horarioCriacao; }
    public LocalDateTime getHorarioSaida() { return horarioSaida; }
    public void setHorarioSaida(LocalDateTime horarioSaida) { this.horarioSaida = horarioSaida; }
    public LocalDateTime getHorarioFinalizacao() { return horarioFinalizacao; }
    public void setHorarioFinalizacao(LocalDateTime horarioFinalizacao) { this.horarioFinalizacao = horarioFinalizacao; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMotivoCancelamento() { return motivoCancelamento; }
    public void setMotivoCancelamento(String motivoCancelamento) { this.motivoCancelamento = motivoCancelamento; }
    public List<ItemPedidoResponseDto> getItens() { return itens; }
    public void setItens(List<ItemPedidoResponseDto> itens) { this.itens = itens; }
}
