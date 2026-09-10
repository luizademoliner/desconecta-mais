package br.com.tcc.desconecta_mais.service;

import br.com.tcc.desconecta_mais.database.entity.SessaoFocoEntity;
import br.com.tcc.desconecta_mais.database.entity.TarefaEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.database.repository.ITarefaRepository;
import br.com.tcc.desconecta_mais.dto.TarefaRequestDto;
import br.com.tcc.desconecta_mais.dto.TarefaResponseDto;
import br.com.tcc.desconecta_mais.exception.BadRequestException;
import br.com.tcc.desconecta_mais.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TarefaService {

    private final ITarefaRepository tarefaRepository;
    private final SessaoFocoService sessaoFocoService;

    private static final int MAX_TAREFAS_POR_SESSAO = 20;

    @Transactional
    public TarefaResponseDto criar(UsuarioEntity usuario, Long sessaoFocoId, TarefaRequestDto dto) throws BadRequestException, NotFoundException {
        SessaoFocoEntity sessao = sessaoFocoService.buscarSessaoDoUsuario(usuario, sessaoFocoId);

        long total = tarefaRepository.findAllBySessaoFocoOrderByDataCriacaoAsc(sessao).size();
        if (total >= MAX_TAREFAS_POR_SESSAO) {
            throw new BadRequestException("Limite de tarefas atingido");
        }

        TarefaEntity tarefa = TarefaEntity.builder()
                .titulo(dto.getTitulo())
                .concluida(false)
                .dataCriacao(LocalDateTime.now())
                .sessaoFoco(sessao)
                .build();

        tarefaRepository.save(tarefa);
        return paraDto(tarefa);
    }

    public List<TarefaResponseDto> listar(UsuarioEntity usuario, Long sessaoFocoId) throws NotFoundException {
        SessaoFocoEntity sessao = sessaoFocoService.buscarSessaoDoUsuario(usuario, sessaoFocoId);
        return tarefaRepository.findAllBySessaoFocoOrderByDataCriacaoAsc(sessao)
                .stream().map(this::paraDto).collect(Collectors.toList());
    }

    @Transactional
    public TarefaResponseDto alternarConcluida(UsuarioEntity usuario, Long tarefaId) throws NotFoundException {
        TarefaEntity tarefa = buscarDoUsuario(usuario, tarefaId);
        tarefa.setConcluida(!tarefa.isConcluida());
        tarefaRepository.save(tarefa);
        return paraDto(tarefa);
    }

    @Transactional
    public void excluir(UsuarioEntity usuario, Long tarefaId) throws NotFoundException {
        TarefaEntity tarefa = buscarDoUsuario(usuario, tarefaId);
        tarefaRepository.delete(tarefa);
    }

    private TarefaEntity buscarDoUsuario(UsuarioEntity usuario, Long tarefaId) throws NotFoundException{
        return tarefaRepository.findByIdAndSessaoFoco_Usuario(tarefaId, usuario)
                .orElseThrow(() -> new NotFoundException("Tarefa não encontrada"));
    }

    private TarefaResponseDto paraDto(TarefaEntity t) {
        return new TarefaResponseDto(t.getId(), t.getTitulo(), t.isConcluida());
    }
}