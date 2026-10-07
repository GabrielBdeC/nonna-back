package br.com.nonna_back.dtos;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada: representa o JSON que o cliente envia para criar/editar
 * uma categoria. Fica separado da Entity porque o que o cliente pode mandar
 * (so o nome) nao e necessariamente igual ao que a tabela guarda (id incluso).
 */
public class CategoriaDto {
    @NotBlank(message = "O nome da categoria é obrigatório")
    private String nome;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
