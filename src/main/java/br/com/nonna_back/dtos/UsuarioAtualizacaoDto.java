package br.com.nonna_back.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Separado do UsuarioDto (usado só no cadastro) porque, ao editar um
 * usuário já existente, a senha é opcional: se vier em branco, mantemos
 * a senha (e o hash) que já estava salva.
 */
public class UsuarioAtualizacaoDto {
    @NotBlank(message = "O nome é obrigatório")
    private String nome;
    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;
    private String senha;
    // Só é exigida quando "senha" vem preenchida: e a prova de que quem
    // está trocando a senha realmente sabe a senha atual, nao so tem
    // o token (que pode ter vazado de uma sessao aberta, por exemplo).
    private String senhaAtual;
    private String telefone;
    @NotBlank(message = "O tipo é obrigatório")
    @Pattern(regexp = "CLIENTE|ADMINISTRADOR", message = "O tipo deve ser CLIENTE ou ADMINISTRADOR")
    private String tipo;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
    public String getSenhaAtual() { return senhaAtual; }
    public void setSenhaAtual(String senhaAtual) { this.senhaAtual = senhaAtual; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
