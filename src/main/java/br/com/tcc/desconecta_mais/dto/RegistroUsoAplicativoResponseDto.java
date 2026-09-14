package br.com.tcc.desconecta_mais.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsoAplicativoResponseDto {

    private Long id;
    private LocalDate data;
    private Long tempoUsoSegundos;
    private String pacote;
}