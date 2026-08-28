package br.com.tcc.desconecta_mais.controller;

import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.dto.RegistroTempoTelaRequestDto;
import br.com.tcc.desconecta_mais.service.RegistroTempoTelaService;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/tempo-tela")
@RequiredArgsConstructor
public class RegistroTempoTelaController {


    private final RegistroTempoTelaService registroTempoTelaService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registrar (@RequestBody @Valid RegistroTempoTelaRequestDto dto, @AuthenticationPrincipal UsuarioEntity usuario) {

        registroTempoTelaService.registrar(usuario, dto.getData(), dto.getTempoTotalSegundos());

    }

}
