package br.com.nonna_back.mappers;

import br.com.nonna_back.dtos.CategoriaDto;
import br.com.nonna_back.dtos.CategoriaResponseDto;
import br.com.nonna_back.entities.Categoria;
import org.springframework.stereotype.Component;

/**
 * O Mapper e o unico lugar que sabe converter entre DTO <-> Entity.
 * Sem ele, esse "copia campo por campo" ficaria espalhado por Controllers
 * e Services, e qualquer mudanca de formato exigiria cacar esse codigo
 * em varios lugares.
 */
@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaDto dto) {
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        return categoria;
    }

    public CategoriaResponseDto toResponseDto(Categoria categoria) {
        CategoriaResponseDto dto = new CategoriaResponseDto();
        dto.setId(categoria.getId());
        dto.setNome(categoria.getNome());
        return dto;
    }
}
