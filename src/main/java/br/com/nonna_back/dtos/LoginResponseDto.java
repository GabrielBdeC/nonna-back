package br.com.nonna_back.dtos;

public class LoginResponseDto {
    private String token;
    private UsuarioResponseDto usuario;

    public LoginResponseDto(String token, UsuarioResponseDto usuario) {
        this.token = token;
        this.usuario = usuario;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public UsuarioResponseDto getUsuario() { return usuario; }
    public void setUsuario(UsuarioResponseDto usuario) { this.usuario = usuario; }
}
