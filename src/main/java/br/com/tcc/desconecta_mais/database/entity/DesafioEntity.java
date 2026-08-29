package br.com.tcc.desconecta_mais.database.entity;

import br.com.tcc.desconecta_mais.enums.StatusDesafioEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "desafio")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class DesafioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String nome;
    private String descricao;

    @Column (name = "codigo_acesso", nullable = false, unique = true)
    private String codigoAcesso;

    @Column (name = "limite_tempo_segundos", nullable = false)
    private Long limiteTempoSegundos;

    @Column (name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column (name = "data_fim", nullable = false)
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private StatusDesafioEnum status;

    @ManyToOne
    @JoinColumn (name = "criador_id", nullable = false)
    private UsuarioEntity criador;

    @ManyToMany
    @JoinTable (
            name = "desafio_aplicativo",
            joinColumns = @JoinColumn(name = "desafio_id"),
            inverseJoinColumns = @JoinColumn(name = "aplicativo_id")
    )

    @Builder.Default
    private Set<AplicativoEntity> aplicativosContabilizados = new HashSet<>();

}
