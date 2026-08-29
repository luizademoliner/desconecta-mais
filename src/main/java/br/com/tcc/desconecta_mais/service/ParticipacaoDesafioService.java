package br.com.tcc.desconecta_mais.service;

import br.com.tcc.desconecta_mais.database.entity.DesafioEntity;
import br.com.tcc.desconecta_mais.database.entity.ParticipacaoDesafioEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.database.repository.IDesafioRepository;
import br.com.tcc.desconecta_mais.database.repository.IParticipacaoDesafioRepository;
import br.com.tcc.desconecta_mais.dto.ParticipacaoDesafioResponseDto;
import br.com.tcc.desconecta_mais.enums.StatusDesafioEnum;
import br.com.tcc.desconecta_mais.enums.StatusParticipacaoDesafioEnum;
import br.com.tcc.desconecta_mais.exception.BadRequestException;
import br.com.tcc.desconecta_mais.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ParticipacaoDesafioService {

    private final IParticipacaoDesafioRepository participacaoDesafioRepository;
    private final IDesafioRepository desafioRepository;

    @Transactional
    public ParticipacaoDesafioResponseDto entrarNoDesafio(UsuarioEntity usuario, String codigoAcesso) throws NotFoundException, BadRequestException {
        DesafioEntity desafio = desafioRepository.findByCodigoAcesso(codigoAcesso)
                .orElseThrow(() -> new NotFoundException("Desafio não encontrado com o código fornecido"));

        if (desafio.getStatus() != StatusDesafioEnum.ATIVO) {
            throw new BadRequestException("Este desafio não está mais ativo");
        }

        if (participacaoDesafioRepository.existsByUsuarioAndDesafio(usuario, desafio)) {
            throw new BadRequestException("Você já está participando deste desafio");
        }

        ParticipacaoDesafioEntity participacao = ParticipacaoDesafioEntity.builder()
                .usuario(usuario)
                .desafio(desafio)
                .dataEntrada(LocalDate.now())
                .pontuacaoTotal(0)
                .status(StatusParticipacaoDesafioEnum.ATIVO)
                .build();

        participacaoDesafioRepository.save(participacao);
        return paraDto(participacao);
    }

    @Transactional
    public ParticipacaoDesafioResponseDto sairDoDesafio(UsuarioEntity usuario, Long desafioId) throws NotFoundException, BadRequestException {
        ParticipacaoDesafioEntity participacao = participacaoDesafioRepository.findByUsuarioAndDesafioId(usuario, desafioId)
                .orElseThrow(() -> new NotFoundException("Participação não encontrada"));

        participacao.setStatus(StatusParticipacaoDesafioEnum.INATIVO);
        participacaoDesafioRepository.save(participacao);
        return paraDto(participacao);
    }

    public List<ParticipacaoDesafioResponseDto> listarMinhasParticipacoes(UsuarioEntity usuario) {
        return participacaoDesafioRepository.findAllByUsuario(usuario).stream()
                .map(this::paraDto)
                .collect(Collectors.toList());
    }

    private ParticipacaoDesafioResponseDto paraDto(ParticipacaoDesafioEntity participacao) {
        return new ParticipacaoDesafioResponseDto(
                participacao.getId(),
                participacao.getDesafio().getId(),
                participacao.getDesafio().getNome(),
                participacao.getDataEntrada(),
                participacao.getPontuacaoTotal(),
                participacao.getStatus()
        );
    }
}