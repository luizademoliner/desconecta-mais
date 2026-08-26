package br.com.tcc.desconecta_mais.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "firebase_uid", nullable = false, unique = true)
    private String firebaseUid;

    @Column(name = "data_inicio_sequencia", nullable = false)
    private LocalDateTime dataInicioSequencia;

    @Column(name = "pontos_gerais", nullable = false)
    private Integer pontosGerais = 0;

    @Column(name = "sequencia_dias", nullable = false)
    private Integer sequenciaDias = 0;

    @Column(name = "imagem_perfil")
    private String imagemPerfil;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @Builder.Default
    private Set<RolesEntity> roles = new HashSet<>();
}