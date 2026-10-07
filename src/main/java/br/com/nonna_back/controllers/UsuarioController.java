package br.com.nonna_back.controllers;

import br.com.nonna_back.dtos.PageDto;
import br.com.nonna_back.dtos.UsuarioDto;
import br.com.nonna_back.dtos.UsuarioResponseDto;
import br.com.nonna_back.entities.Usuario;
import br.com.nonna_back.mappers.UsuarioMapper;
import br.com.nonna_back.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioService service;
    private final UsuarioMapper mapper;

    public UsuarioController(UsuarioService service, UsuarioMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDto criar(@RequestBody @Valid UsuarioDto dto) {
        return mapper.toResponseDto(service.criar(mapper.toEntity(dto)));
    }

    @GetMapping
    public PageDto<UsuarioResponseDto> listar(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "30") int size) {
        PageDto<Usuario> pagina = service.listar(page, size);
        return new PageDto<>(pagina.getContent().stream().map(mapper::toResponseDto).collect(Collectors.toList()),
                pagina.getPage(), pagina.getSize(), pagina.getTotalElements());
    }

    @GetMapping("/{id}")
    public UsuarioResponseDto buscarPorId(@PathVariable String id) {
        return mapper.toResponseDto(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public UsuarioResponseDto atualizar(@PathVariable String id, @RequestBody @Valid UsuarioDto dto) {
        return mapper.toResponseDto(service.atualizar(id, mapper.toEntity(dto)));
    }
}
