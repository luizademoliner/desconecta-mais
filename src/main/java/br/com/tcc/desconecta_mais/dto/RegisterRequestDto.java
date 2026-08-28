package br.com.tcc.desconecta_mais.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RegisterRequestDto {

    @NotBlank // impede valores vazios ou de outro tipo
    private String nome;
}

