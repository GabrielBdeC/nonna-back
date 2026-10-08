package br.com.nonna_back.controllers;

import br.com.nonna_back.dtos.PageDto;
import br.com.nonna_back.dtos.UsuarioAtualizacaoDto;
import br.com.nonna_back.dtos.UsuarioDto;
import br.com.nonna_back.dtos.UsuarioResponseDto;
import br.com.nonna_back.entities.Usuario;
import br.com.nonna_back.mappers.UsuarioMapper;
import br.com.nonna_back.security.Autorizacao;
import br.com.nonna_back.security.UsuarioAutenticado;
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
        // Rota publica (e assim que um cliente novo se cadastra), mas só um
        // ADMINISTRADOR já logado pode criar outro ADMINISTRADOR -- qualquer
        // outro chamador vira CLIENTE, não importa o que mandou no corpo.
        if (!UsuarioAutenticado.ehAdministrador()) {
            dto.setTipo("CLIENTE");
        }
        return mapper.toResponseDto(service.criar(mapper.toEntity(dto)));
    }

    @GetMapping
    public PageDto<UsuarioResponseDto> listar(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "30") int size) {
        Autorizacao.exigirAdministrador();
        PageDto<Usuario> pagina = service.listar(page, size);
        return new PageDto<>(pagina.getContent().stream().map(mapper::toResponseDto).collect(Collectors.toList()),
                pagina.getPage(), pagina.getSize(), pagina.getTotalElements());
    }

    @GetMapping("/{id}")
    public UsuarioResponseDto buscarPorId(@PathVariable String id) {
        Autorizacao.exigirProprioOuAdministrador(id);
        return mapper.toResponseDto(service.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public UsuarioResponseDto atualizar(@PathVariable String id, @RequestBody @Valid UsuarioAtualizacaoDto dto) {
        Autorizacao.exigirProprioOuAdministrador(id);

        // Senha: só o próprio dono da conta troca a dela. Se um administrador
        // está editando outra pessoa, qualquer senha enviada é ignorada.
        if (!id.equals(UsuarioAutenticado.obterId())) {
            dto.setSenha(null);
        }

        // Tipo: só administrador pode promover/rebaixar alguém. Sem essa
        // checagem, um CLIENTE editando o PRÓPRIO cadastro (rota liberada
        // pela regra "próprio ou admin" acima) poderia mandar
        // tipo=ADMINISTRADOR e se autopromover.
        if (!UsuarioAutenticado.ehAdministrador()) {
            dto.setTipo(service.buscarPorId(id).getTipo());
        }

        return mapper.toResponseDto(service.atualizar(id, dto));
    }
}
