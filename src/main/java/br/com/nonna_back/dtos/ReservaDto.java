package br.com.nonna_back.dtos;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public class ReservaDto {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;
    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;
    @NotNull(message = "A data e horário da reserva são obrigatórios")
    @Future(message = "A reserva deve ser para uma data futura")
    private LocalDateTime dataHora;
    @Min(value = 1, message = "A quantidade de pessoas deve ser maior que zero")
    private int quantidadePessoas;
    @NotBlank(message = "A área preferida é obrigatória")
    @Pattern(regexp = "INTERNA|EXTERNA|INDIFERENTE", message = "A área deve ser INTERNA, EXTERNA ou INDIFERENTE")
    private String areaPreferida;
    private boolean precisaCadeirao;
    private boolean comemoracaoAniversario;
    private boolean precisaAcessibilidade;
    private String observacoes;

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
}
