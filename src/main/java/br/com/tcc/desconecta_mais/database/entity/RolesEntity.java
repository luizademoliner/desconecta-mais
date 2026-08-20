package br.com.tcc.desconecta_mais.database.entity;


import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class RolesEntity implements GrantedAuthority {

    //Essa entidade serve para cadastrar alguns papeis

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;

    @Override
    public @Nullable String getAuthority() {
        return nome;
    }
}
