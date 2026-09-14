package br.com.tcc.desconecta_mais.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class AplicativoRequestDto {

    @NotBlank
    private String nome;
    @NotBlank
    private String pacote;
}