package br.com.nonna_back.controllers;

import br.com.nonna_back.dtos.PageDto;
import br.com.nonna_back.dtos.ReservaDto;
import br.com.nonna_back.dtos.ReservaResponseDto;
import br.com.nonna_back.entities.Reserva;
import br.com.nonna_back.mappers.ReservaMapper;
import br.com.nonna_back.security.Autorizacao;
import br.com.nonna_back.services.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reservas")
public class ReservaController {
    private final ReservaService service;
    private final ReservaMapper mapper;

    public ReservaController(ReservaService service, ReservaMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservaResponseDto criar(@RequestBody @Valid ReservaDto dto) {
        Autorizacao.exigirNaoAdministrador();
        return mapper.toResponseDto(service.criar(mapper.toEntity(dto)));
    }

    @GetMapping
    public PageDto<ReservaResponseDto> listar(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "30") int size) {
        Autorizacao.exigirAdministrador();
        PageDto<Reserva> pagina = service.listar(page, size);
        return new PageDto<>(pagina.getContent().stream().map(mapper::toResponseDto).collect(Collectors.toList()),
                pagina.getPage(), pagina.getSize(), pagina.getTotalElements());
    }

    @GetMapping("/{id}")
    public ReservaResponseDto buscarPorId(@PathVariable String id) {
        Autorizacao.exigirAdministrador();
        return mapper.toResponseDto(service.buscarPorId(id));
    }

    @PostMapping("/{id}/cancelar")
    public ReservaResponseDto cancelar(@PathVariable String id, @RequestBody Map<String, String> body) {
        return mapper.toResponseDto(service.cancelar(id, body.get("motivo")));
    }
}
