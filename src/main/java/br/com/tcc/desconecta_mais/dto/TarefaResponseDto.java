package br.com.tcc.desconecta_mais.dto;

public class TarefaResponseDto {
    private Long id;
    private String titulo;
    private boolean concluida;

    public TarefaResponseDto(Long id, String titulo, boolean concluida) {
        this.id = id;
        this.titulo = titulo;
        this.concluida = concluida;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public boolean isConcluida() { return concluida; }
}