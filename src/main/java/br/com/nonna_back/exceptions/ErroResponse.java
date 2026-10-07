package br.com.nonna_back.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public class ErroResponse {
    private int status;
    private List<String> erros;
    private LocalDateTime horario;

    public ErroResponse(int status, List<String> erros) {
        this.status = status;
        this.erros = erros;
        this.horario = LocalDateTime.now();
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    public List<String> getErros() { return erros; }
    public void setErros(List<String> erros) { this.erros = erros; }
    public LocalDateTime getHorario() { return horario; }
    public void setHorario(LocalDateTime horario) { this.horario = horario; }
}
