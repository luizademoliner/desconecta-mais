package br.com.tcc.desconecta_mais.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FocoDiaDto {
    private String diaAbreviado; // "Seg", "Ter", etc.
    private long tempoSegundos;

}
