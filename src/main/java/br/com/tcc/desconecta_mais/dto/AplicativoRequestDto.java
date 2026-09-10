package br.com.tcc.desconecta_mais.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

//Usado para receber os dados do celular

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
