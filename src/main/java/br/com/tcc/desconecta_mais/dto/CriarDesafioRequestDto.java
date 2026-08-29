package br.com.tcc.desconecta_mais.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CriarDesafioRequestDto {

    @NotBlank(message = "O nome do desafio é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "O limite de tempo em segundos é obrigatório")
    @Positive(message = "O limite de tempo deve ser maior que zero")
    private Long limiteTempoSegundos;

    @NotNull(message = "A data de início é obrigatória")
    private LocalDateTime dataInicio;

    @NotNull(message = "A data de fim é obrigatória")
    @Future(message = "A data de fim deve ser no futuro")
    private LocalDateTime dataFim;

    @NotEmpty(message = "Informe ao menos um aplicativo contabilizado para o desafio")
    private List<String> pacotesContabilizados;
}