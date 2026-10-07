package br.com.nonna_back.dtos;

/**
 * Note que nao existe campo "senha" aqui: o Mapper nunca copia esse campo
 * da Entity para este DTO, entao a senha (mesmo em texto puro, por ora)
 * nunca aparece numa resposta da API.
 */
public class UsuarioResponseDto {
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String tipo;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
