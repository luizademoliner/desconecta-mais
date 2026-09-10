package br.com.tcc.desconecta_mais.dto;

//Usado para formatar a resposta que volta para a tela do aplicativo.


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AplicativoResponseDto {

    private Long id;
    private String nome;
    private String pacote;

}
