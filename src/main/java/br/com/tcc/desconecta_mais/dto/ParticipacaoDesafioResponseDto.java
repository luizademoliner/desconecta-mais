package br.com.tcc.desconecta_mais.dto;

import br.com.tcc.desconecta_mais.enums.StatusParticipacaoDesafioEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipacaoDesafioResponseDto {

    private Long id;
    private Long desafioId;
    private String nomeDesafio;
    private LocalDate dataEntrada;
    private Integer pontuacaoTotal;
    private StatusParticipacaoDesafioEnum status;
}