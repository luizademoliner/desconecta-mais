package br.com.tcc.desconecta_mais.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "aplicativo")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class AplicativoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String pacote; // package name, ex: com.instagram.android
}