package br.com.tcc.desconecta_mais.service;

import br.com.tcc.desconecta_mais.config.TokenProvider;
import br.com.tcc.desconecta_mais.database.entity.RolesEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.database.repository.IRolesRepository;
import br.com.tcc.desconecta_mais.database.repository.IUsuarioRepository;
import br.com.tcc.desconecta_mais.dto.LoginRequestDto;
import br.com.tcc.desconecta_mais.dto.RegisterRequestDto;
import br.com.tcc.desconecta_mais.dto.TokenResponseDto;
import br.com.tcc.desconecta_mais.enums.RoleTypeEnum;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final IUsuarioRepository usuarioRepository;
    private final IRolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    @Value("${JWT_EXPIRATION:900000}")
    private long expirationTime;


    // METODO DE CADASTRO
    public void register(RegisterRequestDto dto) throws BadRequestException {
        UsuarioEntity usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElse(null);

        if (usuario != null){
            throw new BadRequestException("Aluno já cadastrado com este email");
        }

        RolesEntity role = rolesRepository.findByNome(RoleTypeEnum.ROLE_USUARIO.name())
                        .orElseGet(() -> rolesRepository.save(RolesEntity.builder()
                                         .nome(RoleTypeEnum.ROLE_USUARIO.name())
                                   .build()));

        usuarioRepository.save(UsuarioEntity.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .roles(Set.of(role))
                .senha(passwordEncoder.encode(dto.getSenha()))
                .build());
    }

    // METODO DE LOGIN
    public TokenResponseDto login(LoginRequestDto dto) throws Exception {
        try {
            //authentication provider -> userDetailsService -> passworEncode.maches() -> autenticado
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
            String token = tokenProvider.gerarToken(authentication);

            return new TokenResponseDto(token, expirationTime);


        }catch (BadCredentialsException e) {
            throw new BadRequestException("Credenciais inválidas"); //teste para dar commit =)
        } catch(Exception e) {
            throw e;

        }
    }

}
