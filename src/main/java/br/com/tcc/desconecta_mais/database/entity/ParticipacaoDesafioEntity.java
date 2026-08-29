package br.com.tcc.desconecta_mais.database.entity;

import br.com.tcc.desconecta_mais.enums.StatusParticipacaoDesafioEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table (name = "participacao_desafio", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "desafio_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipacaoDesafioEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "data_entrada", nullable = false)
    private LocalDate dataEntrada;

    @ManyToOne
    @Column (name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @Column (name = "desafio_id", nullable = false)
    private DesafioEntity desafio;

    @Column (name = "pontuacao_total", nullable = false)
    private Integer pontuacaoTotal;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false)
    private StatusParticipacaoDesafioEnum status;

}
