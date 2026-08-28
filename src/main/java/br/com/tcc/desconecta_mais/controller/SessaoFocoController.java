package br.com.tcc.desconecta_mais.controller;

import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.dto.IniciarSessaoFocoRequestDto;
import br.com.tcc.desconecta_mais.dto.SessaoFocoResponseDto;
import br.com.tcc.desconecta_mais.exception.BadRequestException;
import br.com.tcc.desconecta_mais.exception.NotFoundException;
import br.com.tcc.desconecta_mais.service.SessaoFocoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/sessoes-foco")
@RequiredArgsConstructor
public class SessaoFocoController {

    private final SessaoFocoService sessaoFocoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessaoFocoResponseDto iniciar(
            @RequestBody @Valid IniciarSessaoFocoRequestDto dto,
            @AuthenticationPrincipal UsuarioEntity usuario) {
        return sessaoFocoService.iniciar(usuario, dto);
    }

    @PatchMapping("/{id}/concluir")
    public SessaoFocoResponseDto concluir(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioEntity usuario) throws NotFoundException, BadRequestException {
        return sessaoFocoService.concluir(usuario, id);
    }

    @PatchMapping("/{id}/cancelar")
    public SessaoFocoResponseDto cancelar(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioEntity usuario) throws BadRequestException, NotFoundException {
        return sessaoFocoService.cancelar(usuario, id);
    }

    @GetMapping("/{id}")
    public SessaoFocoResponseDto buscar(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioEntity usuario) throws NotFoundException {
        return sessaoFocoService.buscar(usuario, id);
    }
}