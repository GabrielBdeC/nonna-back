package br.com.nonna_back.controllers;

import br.com.nonna_back.dtos.CategoriaDto;
import br.com.nonna_back.dtos.CategoriaResponseDto;
import br.com.nonna_back.dtos.PageDto;
import br.com.nonna_back.entities.Categoria;
import br.com.nonna_back.mappers.CategoriaMapper;
import br.com.nonna_back.security.Autorizacao;
import br.com.nonna_back.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService service;
    private final CategoriaMapper mapper;

    public CategoriaController(CategoriaService service, CategoriaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponseDto criar(@RequestBody @Valid CategoriaDto dto) {
        Autorizacao.exigirAdministrador();
        return mapper.toResponseDto(service.criar(mapper.toEntity(dto)));
    }

    @GetMapping
    public PageDto<CategoriaResponseDto> listar(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "30") int size) {
        PageDto<Categoria> pagina = service.listar(page, size);
        return new PageDto<>(pagina.getContent().stream().map(mapper::toResponseDto).collect(Collectors.toList()),
                pagina.getPage(), pagina.getSize(), pagina.getTotalElements());
    }

    @PutMapping("/{id}")
    public CategoriaResponseDto atualizar(@PathVariable String id, @RequestBody @Valid CategoriaDto dto) {
        Autorizacao.exigirAdministrador();
        return mapper.toResponseDto(service.atualizar(id, mapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable String id) {
        Autorizacao.exigirAdministrador();
        service.remover(id);
    }
}
