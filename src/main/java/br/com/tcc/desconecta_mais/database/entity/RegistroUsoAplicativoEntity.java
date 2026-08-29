package br.com.tcc.desconecta_mais.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table (name = "registro_uso_aplicativo", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "aplicativo_id", "data"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RegistroUsoAplicativoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "tempo_uso_segundos", nullable = false)
    private Long tempoUsoSegundos;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "aplicativo_id", nullable = false)
    private AplicativoEntity aplicativo;

}
