package br.com.tcc.desconecta_mais.service;

import br.com.tcc.desconecta_mais.database.entity.AplicativoEntity;
import br.com.tcc.desconecta_mais.database.entity.SessaoFocoEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.database.repository.IAplicativoRepository;
import br.com.tcc.desconecta_mais.database.repository.ISessaoFocoRepository;
import br.com.tcc.desconecta_mais.dto.IniciarSessaoFocoRequestDto;
import br.com.tcc.desconecta_mais.dto.SessaoFocoResponseDto;
import br.com.tcc.desconecta_mais.enums.StatusSessaoFocoEnum;
import br.com.tcc.desconecta_mais.exception.BadRequestException;
import br.com.tcc.desconecta_mais.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessaoFocoService {

    private final ISessaoFocoRepository sessaoFocoRepository;
    private final IAplicativoRepository aplicativoRepository;

    @Transactional
    public SessaoFocoResponseDto iniciar(UsuarioEntity usuario, IniciarSessaoFocoRequestDto dto) {

        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime dataFim = agora.plusMinutes(dto.getDuracaoMinutos());

        Set<AplicativoEntity> aplicativos = dto.getPacotesBloqueados().stream()
                .map(this::buscarOuCriarAplicativo)
                .collect(Collectors.toSet());

        SessaoFocoEntity sessao = SessaoFocoEntity.builder()
                .dataInicio(agora)
                .dataFim(dataFim)
                .status(StatusSessaoFocoEnum.EM_ANDAMENTO)
                .usuario(usuario)
                .aplicativosBloqueados(aplicativos)
                .build();

        sessaoFocoRepository.save(sessao);
        return paraDto(sessao);
    }

    @Transactional
    public SessaoFocoResponseDto concluir(UsuarioEntity usuario, Long sessaoId) throws NotFoundException, BadRequestException {
        SessaoFocoEntity sessao = buscarDoUsuario(usuario, sessaoId);
        validarSessaoEmAndamento(sessao);

        sessao.setStatus(StatusSessaoFocoEnum.CONCLUIDA);
        sessaoFocoRepository.save(sessao);
        return paraDto(sessao);
    }

    @Transactional
    public SessaoFocoResponseDto cancelar(UsuarioEntity usuario, Long sessaoId) throws BadRequestException, NotFoundException {
        SessaoFocoEntity sessao = buscarDoUsuario(usuario, sessaoId);
        validarSessaoEmAndamento(sessao);

        sessao.setStatus(StatusSessaoFocoEnum.CANCELADA);
        sessaoFocoRepository.save(sessao);
        return paraDto(sessao);
    }

    public SessaoFocoResponseDto buscar(UsuarioEntity usuario, Long sessaoId) throws NotFoundException {
        return paraDto(buscarDoUsuario(usuario, sessaoId));
    }

    public SessaoFocoResponseDto buscarAtiva(UsuarioEntity usuario) throws NotFoundException {
        SessaoFocoEntity sessao = sessaoFocoRepository
                .findFirstByUsuarioAndStatusOrderByDataInicioDesc(usuario, StatusSessaoFocoEnum.EM_ANDAMENTO)
                .orElseThrow(() -> new NotFoundException("Nenhuma sessão ativa"));
        return paraDto(sessao);
    }

    // package-private/public: usado também pelo TarefaService
    public SessaoFocoEntity buscarDoUsuario(UsuarioEntity usuario, Long id) throws NotFoundException {
        return sessaoFocoRepository.findByIdAndUsuario(id, usuario)
                .orElseThrow(() -> new NotFoundException("Sessão não encontrada"));
    }

    private void validarSessaoEmAndamento(SessaoFocoEntity sessao) throws BadRequestException {
        if (sessao.getStatus() != StatusSessaoFocoEnum.EM_ANDAMENTO) {
            throw new BadRequestException("Essa sessão já foi finalizada");
        }
    }

    private AplicativoEntity buscarOuCriarAplicativo(String pacote) {
        return aplicativoRepository.findByPacote(pacote)
                .orElseGet(() -> aplicativoRepository.save(
                        AplicativoEntity.builder()
                                .pacote(pacote)
                                .nome(pacote)
                                .build()
                ));
    }

    private SessaoFocoResponseDto paraDto(SessaoFocoEntity sessao) {
        List<String> pacotes = sessao.getAplicativosBloqueados().stream()
                .map(AplicativoEntity::getPacote)
                .collect(Collectors.toList());

        return new SessaoFocoResponseDto(
                sessao.getId(), sessao.getDataInicio(), sessao.getDataFim(),
                sessao.getStatus().name(), pacotes
        );
    }
}