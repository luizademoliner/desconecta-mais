package br.com.tcc.desconecta_mais.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class RegistroTempoTelaRequestDto {

    @NotNull
    private LocalDate data;

    @NotNull
    private Long tempoTotalSegundos;
}