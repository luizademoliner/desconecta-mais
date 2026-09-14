package br.com.tcc.desconecta_mais.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroUsoAplicativoRequestDto {

    @NotNull(message = "A data é obrigatória")
    private LocalDate data;

    @NotNull(message = "O tempo de uso em segundos é obrigatório")
    @PositiveOrZero(message = "O tempo de uso não pode ser negativo")
    private Long tempoUsoSegundos;

    @NotBlank(message = "O pacote do aplicativo é obrigatório")
    private String pacote;
}