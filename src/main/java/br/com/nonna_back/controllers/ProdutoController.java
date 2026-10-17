package br.com.nonna_back.controllers;

import br.com.nonna_back.dtos.PageDto;
import br.com.nonna_back.dtos.ProdutoDto;
import br.com.nonna_back.dtos.ProdutoResponseDto;
import br.com.nonna_back.entities.Produto;
import br.com.nonna_back.mappers.ProdutoMapper;
import br.com.nonna_back.security.Autorizacao;
import br.com.nonna_back.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoService service;
    private final ProdutoMapper mapper;

    public ProdutoController(ProdutoService service, ProdutoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDto criar(@RequestBody @Valid ProdutoDto dto) {
        Autorizacao.exigirAdministrador();
        return mapper.toResponseDto(service.criar(mapper.toEntity(dto)));
    }

    @GetMapping
    public PageDto<ProdutoResponseDto> listar(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "30") int size,
                                               @RequestParam(required = false) String idCategoria) {
        PageDto<Produto> pagina = service.listar(page, size, idCategoria);
        return new PageDto<>(pagina.getContent().stream().map(mapper::toResponseDto).collect(Collectors.toList()),
                pagina.getPage(), pagina.getSize(), pagina.getTotalElements());
    }

    @GetMapping("/{id}")
    public ProdutoResponseDto buscarPorId(@PathVariable String id) {
        return mapper.toResponseDto(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ProdutoResponseDto atualizar(@PathVariable String id, @RequestBody @Valid ProdutoDto dto) {
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
