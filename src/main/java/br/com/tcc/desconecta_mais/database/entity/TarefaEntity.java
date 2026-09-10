package br.com.tcc.desconecta_mais.database.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarefa")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class TarefaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false)
    private boolean concluida;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @ManyToOne
    @JoinColumn(name = "sessao_foco_id", nullable = false)
    private SessaoFocoEntity sessaoFoco;
}