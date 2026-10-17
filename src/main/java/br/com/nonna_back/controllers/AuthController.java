package br.com.nonna_back.controllers;

import br.com.nonna_back.dtos.LoginDto;
import br.com.nonna_back.dtos.LoginResponseDto;
import br.com.nonna_back.dtos.UsuarioResponseDto;
import br.com.nonna_back.entities.Usuario;
import br.com.nonna_back.mappers.UsuarioMapper;
import br.com.nonna_back.security.Autorizacao;
import br.com.nonna_back.security.JwtUtil;
import br.com.nonna_back.security.UsuarioAutenticado;
import br.com.nonna_back.services.AuthService;
import br.com.nonna_back.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;
    private final UsuarioMapper mapper;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public AuthController(AuthService authService, UsuarioMapper mapper, JwtUtil jwtUtil, UsuarioService usuarioService){
        this.authService = authService;
        this.mapper = mapper;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/auth/login")
    public LoginResponseDto login(@RequestBody @Valid LoginDto dto){
        Usuario usuario = authService.login(dto.getEmail(), dto.getSenha());
        String token = jwtUtil.gerarToken(usuario);
        return new LoginResponseDto(token, mapper.toResponseDto(usuario));
    }

    @GetMapping("/me")
    public UsuarioResponseDto me() {
        Autorizacao.exigirAutenticado();
        return mapper.toResponseDto(usuarioService.buscarPorId(UsuarioAutenticado.obterId()));
    }
}
