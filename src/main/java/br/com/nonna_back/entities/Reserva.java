package br.com.nonna_back.entities;

import java.time.LocalDateTime;

public class Reserva {
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private LocalDateTime dataHora;
    private int quantidadePessoas;
    private String areaPreferida; // INTERNA, EXTERNA ou INDIFERENTE
    private boolean precisaCadeirao;
    private boolean comemoracaoAniversario;
    private boolean precisaAcessibilidade;
    private String observacoes;
    private String status; // ATIVA ou CANCELADA
    private String motivoCancelamento;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public int getQuantidadePessoas() { return quantidadePessoas; }
    public void setQuantidadePessoas(int quantidadePessoas) { this.quantidadePessoas = quantidadePessoas; }
    public String getAreaPreferida() { return areaPreferida; }
    public void setAreaPreferida(String areaPreferida) { this.areaPreferida = areaPreferida; }
    public boolean isPrecisaCadeirao() { return precisaCadeirao; }
    public void setPrecisaCadeirao(boolean precisaCadeirao) { this.precisaCadeirao = precisaCadeirao; }
    public boolean isComemoracaoAniversario() { return comemoracaoAniversario; }
    public void setComemoracaoAniversario(boolean comemoracaoAniversario) { this.comemoracaoAniversario = comemoracaoAniversario; }
    public boolean isPrecisaAcessibilidade() { return precisaAcessibilidade; }
    public void setPrecisaAcessibilidade(boolean precisaAcessibilidade) { this.precisaAcessibilidade = precisaAcessibilidade; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMotivoCancelamento() { return motivoCancelamento; }
    public void setMotivoCancelamento(String motivoCancelamento) { this.motivoCancelamento = motivoCancelamento; }
}
