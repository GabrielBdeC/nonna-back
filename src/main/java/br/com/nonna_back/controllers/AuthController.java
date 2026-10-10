package br.com.nonna_back.controllers;

import br.com.nonna_back.dtos.LoginDto;
import br.com.nonna_back.dtos.LoginResponseDto;
import br.com.nonna_back.entities.Usuario;
import br.com.nonna_back.mappers.UsuarioMapper;
import br.com.nonna_back.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthService authService;
    private final UsuarioMapper mapper;

    public AuthController(AuthService authService, UsuarioMapper mapper){
        this.authService = authService;
        this.mapper = mapper;
    }

    @PostMapping("/auth/login")
    public LoginResponseDto login(@RequestBody @Valid LoginDto dto){
        Usuario usuario = authService.login(dto.getEmail(), dto.getSenha());
        return new LoginResponseDto("TOKEN_VAZIO", mapper.toResponseDto(usuario));
    }
}
