package br.com.tcc.desconecta_mais.dto;

import java.time.LocalDateTime;
import java.util.List;

public class SessaoFocoResponseDto {
    private Long id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private String status;
    private List<String> pacotesBloqueados;

    public SessaoFocoResponseDto(Long id, LocalDateTime dataInicio, LocalDateTime dataFim,
                                 String status, List<String> pacotesBloqueados) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.pacotesBloqueados = pacotesBloqueados;
    }

    public Long getId() { return id; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
    public String getStatus() { return status; }
    public List<String> getPacotesBloqueados() { return pacotesBloqueados; }
}