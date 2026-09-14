package br.com.tcc.desconecta_mais.controller;

import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.dto.TarefaRequestDto;
import br.com.tcc.desconecta_mais.dto.TarefaResponseDto;
import br.com.tcc.desconecta_mais.exception.BadRequestException;
import br.com.tcc.desconecta_mais.exception.NotFoundException;
import br.com.tcc.desconecta_mais.service.TarefaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/sessoes-foco/{sessaoFocoId}/tarefas")
@RequiredArgsConstructor
public class TarefaController {

    private final TarefaService tarefaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TarefaResponseDto criar(@PathVariable Long sessaoFocoId,
                                   @RequestBody @Valid TarefaRequestDto dto,
                                   @AuthenticationPrincipal UsuarioEntity usuario) throws BadRequestException, NotFoundException {
        return tarefaService.criar(usuario, sessaoFocoId, dto);
    }

    @GetMapping
    public List<TarefaResponseDto> listar(@PathVariable Long sessaoFocoId,
                                          @AuthenticationPrincipal UsuarioEntity usuario) throws NotFoundException {
        return tarefaService.listar(usuario, sessaoFocoId);
    }

    @PatchMapping("/{tarefaId}/alternar")
    public TarefaResponseDto alternar(@PathVariable Long tarefaId,
                                      @AuthenticationPrincipal UsuarioEntity usuario) throws NotFoundException {
        return tarefaService.alternarConcluida(usuario, tarefaId);
    }

    @DeleteMapping("/{tarefaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long tarefaId, @AuthenticationPrincipal UsuarioEntity usuario) throws NotFoundException {
        tarefaService.excluir(usuario, tarefaId);
    }
}