package br.com.tcc.desconecta_mais.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TarefaRequestDto {
    @NotBlank
    @Size(max = 200)
    private String titulo;

    public String getTitulo() { return titulo; }
    public void setTitulo(String v) { this.titulo = v; }
}