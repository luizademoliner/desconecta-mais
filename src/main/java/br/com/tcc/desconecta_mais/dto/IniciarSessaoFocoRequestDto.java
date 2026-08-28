package br.com.tcc.desconecta_mais.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class IniciarSessaoFocoRequestDto {

    @NotBlank
    @Size(max = 100)
    private String titulo;

    @Size(max = 500)
    private String objetivo;

    @NotNull
    @Min(1)
    @Max(240) // limite de 4h por sessão - evita valores absurdos
    private Integer duracaoMinutos;

    @NotEmpty
    @Size(max = 30) // limite razoável de apps por sessão
    private List<String> pacotesBloqueados; // package names dos apps escolhidos

}