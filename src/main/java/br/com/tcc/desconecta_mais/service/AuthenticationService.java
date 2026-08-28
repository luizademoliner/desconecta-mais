package br.com.tcc.desconecta_mais.service;

import br.com.tcc.desconecta_mais.database.entity.RolesEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.database.repository.IRolesRepository;
import br.com.tcc.desconecta_mais.database.repository.IUsuarioRepository;
import br.com.tcc.desconecta_mais.dto.RegisterRequestDto;
import br.com.tcc.desconecta_mais.enums.RoleTypeEnum;
import com.google.firebase.auth.FirebaseToken;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUsuarioRepository usuarioRepository;
    private final IRolesRepository rolesRepository;

    public void register(RegisterRequestDto dto, FirebaseToken firebaseToken) throws BadRequestException {

        String firebaseUid = firebaseToken.getUid();
        String email = firebaseToken.getEmail();

        // Verifica se o UID do Firebase já está cadastrado
        if (usuarioRepository.findByFirebaseUid(firebaseUid).isPresent()) {
            throw new BadRequestException("Usuário já cadastrado");
        }

        // Verifica se o email já está cadastrado
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new BadRequestException("Email já cadastrado");
        }

        RolesEntity role = rolesRepository
                .findByNome(RoleTypeEnum.ROLE_USUARIO.name())
                .orElseGet(() -> rolesRepository.save(
                        RolesEntity.builder()
                        .nome(RoleTypeEnum.ROLE_USUARIO.name())
                        .build()
                ));

        UsuarioEntity usuario = UsuarioEntity.builder()
                .firebaseUid(firebaseUid)
                .nome(dto.getNome())
                .email(email)
                .dataInicioSequencia(LocalDateTime.now())
                .pontosGerais(0)
                .sequenciaDias(0)
                .roles(Set.of(role))
                .build();

        usuarioRepository.save(usuario);
    }

    public boolean emailExiste(String email) {                    /*acho que esse metod nnão será mais necessário depois*/
        return usuarioRepository.findByEmail(email).isPresent();  //que a tela de login ficar 100% pronta
    }

    public void confirmarLogin(FirebaseToken firebaseToken) throws BadRequestException {
        String firebaseUid = firebaseToken.getUid();

        UsuarioEntity usuario = usuarioRepository.findByFirebaseUid(firebaseUid)
                .orElseThrow(() -> new BadRequestException("Usuário não encontrado"));

        usuario.setDataInicioSequencia(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }


}