package br.com.tcc.desconecta_mais.controller;

import br.com.tcc.desconecta_mais.dto.LoginRequestDto;
import br.com.tcc.desconecta_mais.dto.RegisterRequestDto;
import br.com.tcc.desconecta_mais.dto.TokenResponseDto;
import br.com.tcc.desconecta_mais.service.AuthenticationService;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequestDto registerRequestDto, HttpServletRequest request) throws Exception {
        FirebaseToken firebaseToken = (FirebaseToken) request.getAttribute("firebaseToken");

        authenticationService.register(registerRequestDto, firebaseToken);
    }

    @PostMapping("/login-confirmado") // <-- novo
    public void loginConfirmado(HttpServletRequest request) throws Exception {
        FirebaseToken firebaseToken = (FirebaseToken) request.getAttribute("firebaseToken");
        authenticationService.confirmarLogin(firebaseToken);
    }
}
