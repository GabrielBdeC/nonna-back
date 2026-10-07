package br.com.nonna_back.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

/**
 * DTO separado do PedidoDto porque atualizar um pedido so muda status e
 * horarios -- nao faz sentido reenviar usuario, itens, etc.
 */
public class PedidoAtualizacaoDto {
    @NotBlank(message = "O status é obrigatório")
    @Pattern(regexp = "CRIADO|EM_PREPARO|SAIU_PARA_ENTREGA|CONCLUIDO|CANCELADO", message = "Status inválido")
    private String status;
    private LocalDateTime horarioSaida;
    private LocalDateTime horarioFinalizacao;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getHorarioSaida() { return horarioSaida; }
    public void setHorarioSaida(LocalDateTime horarioSaida) { this.horarioSaida = horarioSaida; }
    public LocalDateTime getHorarioFinalizacao() { return horarioFinalizacao; }
    public void setHorarioFinalizacao(LocalDateTime horarioFinalizacao) { this.horarioFinalizacao = horarioFinalizacao; }
}
