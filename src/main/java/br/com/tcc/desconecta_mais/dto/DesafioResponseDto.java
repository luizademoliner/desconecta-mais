package br.com.tcc.desconecta_mais.dto;

import br.com.tcc.desconecta_mais.enums.StatusDesafioEnum;
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
public class DesafioResponseDto {

    private Long id;
    private String nome;
    private String descricao;
    private String codigoAcesso;
    private Long limiteTempoSegundos;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private StatusDesafioEnum status;
    private List<String> pacotesContabilizados;
}