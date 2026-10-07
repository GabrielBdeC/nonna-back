package br.com.nonna_back.dtos;

/**
 * DTO de saida: o que a API devolve para o cliente. Aqui coincide com a
 * Entity, mas em outros domínios (ex.: Usuario) o response esconde campos
 * sensiveis (como senha) que a Entity carrega internamente.
 */
public class CategoriaResponseDto {
    private String id;
    private String nome;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
