package br.com.nonna_back.mappers;

import br.com.nonna_back.dtos.UsuarioDto;
import br.com.nonna_back.dtos.UsuarioResponseDto;
import br.com.nonna_back.entities.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioDto dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(dto.getSenha());
        usuario.setTelefone(dto.getTelefone());
        usuario.setTipo(dto.getTipo());
        return usuario;
    }

    // Repare que "senha" nunca e copiada para o ResponseDto: e assim que
    // garantimos que a senha do usuario nunca sai pela API.
    public UsuarioResponseDto toResponseDto(Usuario usuario) {
        UsuarioResponseDto dto = new UsuarioResponseDto();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setTelefone(usuario.getTelefone());
        dto.setTipo(usuario.getTipo());
        return dto;
    }
}
