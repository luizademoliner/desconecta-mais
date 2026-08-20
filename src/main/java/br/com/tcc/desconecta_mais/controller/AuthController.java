package br.com.tcc.desconecta_mais.controller;

import br.com.tcc.desconecta_mais.dto.LoginRequestDto;
import br.com.tcc.desconecta_mais.dto.RegisterRequestDto;
import br.com.tcc.desconecta_mais.dto.TokenResponseDto;
import br.com.tcc.desconecta_mais.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequestDto registerRequestDto) throws Exception {
        authenticationService.register(registerRequestDto);
    }

    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody @Valid LoginRequestDto loginRequestDto) throws Exception {
        return authenticationService.login(loginRequestDto);
    }
}
