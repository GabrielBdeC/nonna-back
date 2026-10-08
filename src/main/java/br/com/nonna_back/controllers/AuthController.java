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
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final AuthService authService;
    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, UsuarioService usuarioService, UsuarioMapper mapper, JwtUtil jwtUtil) {
        this.authService = authService;
        this.usuarioService = usuarioService;
        this.mapper = mapper;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/login")
    public LoginResponseDto login(@RequestBody @Valid LoginDto dto) {
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
