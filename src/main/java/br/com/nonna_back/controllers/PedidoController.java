package br.com.nonna_back.controllers;

import br.com.nonna_back.dtos.*;
import br.com.nonna_back.entities.Pedido;
import br.com.nonna_back.entities.ProdutoPedido;
import br.com.nonna_back.mappers.PedidoMapper;
import br.com.nonna_back.mappers.ProdutoPedidoMapper;
import br.com.nonna_back.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService service;
    private final PedidoMapper mapper;
    private final ProdutoPedidoMapper itemMapper;

    public PedidoController(PedidoService service, PedidoMapper mapper, ProdutoPedidoMapper itemMapper) {
        this.service = service;
        this.mapper = mapper;
        this.itemMapper = itemMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponseDto criar(@RequestBody @Valid PedidoDto dto) {
        List<ProdutoPedido> itensSolicitados = dto.getItens().stream().map(item -> {
            ProdutoPedido entidade = new ProdutoPedido();
            entidade.setIdProduto(item.getIdProduto());
            entidade.setQuantidade(item.getQuantidade());
            return entidade;
        }).collect(Collectors.toList());

        Pedido pedido = service.criar(mapper.toEntity(dto), itensSolicitados);
        PedidoResponseDto resposta = mapper.toResponseDto(pedido);
        resposta.setItens(itensSolicitados.stream().map(itemMapper::toResponseDto).collect(Collectors.toList()));
        return resposta;
    }

    @GetMapping
    public PageDto<PedidoResponseDto> listar(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "30") int size) {
        PageDto<Pedido> pagina = service.listarNaoConcluidos(page, size);
        return new PageDto<>(pagina.getContent().stream().map(mapper::toResponseDto).collect(Collectors.toList()),
                pagina.getPage(), pagina.getSize(), pagina.getTotalElements());
    }

    @GetMapping("/{id}")
    public PedidoResponseDto buscarPorId(@PathVariable String id) {
        PedidoResponseDto resposta = mapper.toResponseDto(service.buscarPorId(id));
        resposta.setItens(service.buscarItens(id).stream().map(itemMapper::toResponseDto).collect(Collectors.toList()));
        return resposta;
    }

    @PutMapping("/{id}")
    public PedidoResponseDto atualizar(@PathVariable String id, @RequestBody @Valid PedidoAtualizacaoDto dto) {
        return mapper.toResponseDto(service.atualizar(id, dto.getStatus(), dto.getHorarioSaida(), dto.getHorarioFinalizacao()));
    }

    @PostMapping("/{id}/cancelar")
    public PedidoResponseDto cancelar(@PathVariable String id, @RequestBody Map<String, String> body) {
        return mapper.toResponseDto(service.cancelar(id, body.get("motivo")));
    }
}
