package br.com.tcc.desconecta_mais.dto;

import br.com.tcc.desconecta_mais.enums.StatusSessaoFocoEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class SessaoFocoResponseDto {
    private Long id;
    private String titulo;
    private String objetivo;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private StatusSessaoFocoEnum status;
    private List<String> pacotesBloqueados;

    /*public SessaoFocoResponseDto(Long id, String titulo, String objetivo,
                                 LocalDateTime dataInicio, LocalDateTime dataFim,
                                 StatusSessaoFocoEnum status, List<String> pacotesBloqueados) {
        this.id = id;
        this.titulo = titulo;
        this.objetivo = objetivo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.pacotesBloqueados = pacotesBloqueados;
    }*/

}