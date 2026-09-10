package br.com.tcc.desconecta_mais.dto;

import jakarta.validation.Valid;
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

    @NotNull @Min(1) @Max(240)
    private Integer duracaoMinutos;

    @NotEmpty @Size(max = 30)
    private List<String> pacotesBloqueados;

}