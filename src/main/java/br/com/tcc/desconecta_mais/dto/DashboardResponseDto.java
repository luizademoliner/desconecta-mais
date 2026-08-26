package br.com.tcc.desconecta_mais.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class DashboardResponseDto {

    private long tempoHojeSegundos;
    private int pontosGerais;
    private long diasDeSequencia;
    private long mediaDiariaSegundosSemana;
    private List<FocoDiaDto> tempoPorDiaSemana;

}
