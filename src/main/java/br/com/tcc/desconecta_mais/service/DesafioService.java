package br.com.tcc.desconecta_mais.service;

import br.com.tcc.desconecta_mais.database.entity.AplicativoEntity;
import br.com.tcc.desconecta_mais.database.entity.DesafioEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.database.repository.IAplicativoRepository;
import br.com.tcc.desconecta_mais.database.repository.IDesafioRepository;
import br.com.tcc.desconecta_mais.dto.CriarDesafioRequestDto;
import br.com.tcc.desconecta_mais.dto.DesafioResponseDto;
import br.com.tcc.desconecta_mais.enums.StatusDesafioEnum;
import br.com.tcc.desconecta_mais.exception.BadRequestException;
import br.com.tcc.desconecta_mais.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DesafioService {

    private final IDesafioRepository desafioRepository;
    private final IAplicativoRepository aplicativoRepository;

    @Transactional
    public DesafioResponseDto criar(UsuarioEntity criador, CriarDesafioRequestDto dto) {
        Set<AplicativoEntity> aplicativos = dto.getPacotesContabilizados().stream()
                .map(this::buscarOuCriarAplicativo)
                .collect(Collectors.toSet());

        String codigoAcesso = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        DesafioEntity desafio = DesafioEntity.builder()
                .nome(dto.getNome())
                .descricao(dto.getDescricao())
                .codigoAcesso(codigoAcesso)
                .limiteTempoSegundos(dto.getLimiteTempoSegundos())
                .dataInicio(dto.getDataInicio())
                .dataFim(dto.getDataFim())
                .status(StatusDesafioEnum.ATIVO)
                .criador(criador)
                .aplicativosContabilizados(aplicativos)
                .build();

        desafioRepository.save(desafio);
        return paraDto(desafio);
    }

    public DesafioResponseDto buscarPorId(Long desafioId) throws NotFoundException {
        DesafioEntity desafio = desafioRepository.findById(desafioId)
                .orElseThrow(() -> new NotFoundException("Desafio não encontrado"));
        return paraDto(desafio);
    }

    public DesafioResponseDto buscarPorCodigoAcesso(String codigoAcesso) throws NotFoundException {
        DesafioEntity desafio = desafioRepository.findByCodigoAcesso(codigoAcesso)
                .orElseThrow(() -> new NotFoundException("Desafio não encontrado para este código"));
        return paraDto(desafio);
    }

    @Transactional
    public DesafioResponseDto cancelar(UsuarioEntity usuario, Long desafioId) throws NotFoundException, BadRequestException {
        DesafioEntity desafio = desafioRepository.findById(desafioId)
                .orElseThrow(() -> new NotFoundException("Desafio não encontrado"));

        if (!desafio.getCriador().getId().equals(usuario.getId())) {
            throw new BadRequestException("Apenas o criador pode cancelar o desafio");
        }

        desafio.setStatus(StatusDesafioEnum.CANCELADO);
        desafioRepository.save(desafio);
        return paraDto(desafio);
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

    private DesafioResponseDto paraDto(DesafioEntity desafio) {
        List<String> pacotes = desafio.getAplicativosContabilizados().stream()
                .map(AplicativoEntity::getPacote)
                .collect(Collectors.toList());

        return new DesafioResponseDto(
                desafio.getId(), desafio.getNome(), desafio.getDescricao(),
                desafio.getCodigoAcesso(), desafio.getLimiteTempoSegundos(),
                desafio.getDataInicio(), desafio.getDataFim(), desafio.getStatus(), pacotes
        );
    }
}