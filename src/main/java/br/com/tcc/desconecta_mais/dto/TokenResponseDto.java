package br.com.tcc.desconecta_mais.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TokenResponseDto { // poderia ser usado record ao invés de class

    private String token;
    private long expiresIn;

}
