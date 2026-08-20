package br.com.tcc.desconecta_mais.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LoginRequestDto {

    @NotBlank
    private String email;
    @NotBlank
    private String senha;

}
