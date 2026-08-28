package br.com.tcc.desconecta_mais.database.entity;

import br.com.tcc.desconecta_mais.enums.StatusSessaoFocoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sessao_foco")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class SessaoFocoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String objetivo;

    @Column(name = "data_inicio", nullable = false)
    private LocalDateTime dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSessaoFocoEnum status;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToMany
    @JoinTable(
            name = "sessao_foco_aplicativo",
            joinColumns = @JoinColumn(name = "sessao_foco_id"),
            inverseJoinColumns = @JoinColumn(name = "aplicativo_id")
    )
    @Builder.Default
    private Set<AplicativoEntity> aplicativosBloqueados = new HashSet<>();
}