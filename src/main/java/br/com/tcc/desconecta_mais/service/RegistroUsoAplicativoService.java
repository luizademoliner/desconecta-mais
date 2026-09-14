package br.com.tcc.desconecta_mais.service;

import br.com.tcc.desconecta_mais.database.entity.AplicativoEntity;
import br.com.tcc.desconecta_mais.database.entity.RegistroUsoAplicativoEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.database.repository.IAplicativoRepository;
import br.com.tcc.desconecta_mais.database.repository.IRegistroUsoAplicativoRepository;
import br.com.tcc.desconecta_mais.dto.RegistroUsoAplicativoRequestDto;
import br.com.tcc.desconecta_mais.dto.RegistroUsoAplicativoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistroUsoAplicativoService {

    private final IRegistroUsoAplicativoRepository registroUsoAplicativoRepository;
    private final IAplicativoRepository aplicativoRepository;

    @Transactional
    public RegistroUsoAplicativoResponseDto registrarUso(UsuarioEntity usuario, RegistroUsoAplicativoRequestDto dto) {
        AplicativoEntity aplicativo = buscarOuCriarAplicativo(dto.getPacote());

        RegistroUsoAplicativoEntity registro = registroUsoAplicativoRepository
                .findByUsuarioAndAplicativoAndData(usuario, aplicativo, dto.getData())
                .orElseGet(() -> RegistroUsoAplicativoEntity.builder()
                        .usuario(usuario)
                        .aplicativo(aplicativo)
                        .data(dto.getData())
                        .tempoUsoSegundos(0L)
                        .build());

        registro.setTempoUsoSegundos(dto.getTempoUsoSegundos());
        registroUsoAplicativoRepository.save(registro);

        return paraDto(registro);
    }

    public List<RegistroUsoAplicativoResponseDto> listarPorData(UsuarioEntity usuario, java.time.LocalDate data) {
        return registroUsoAplicativoRepository.findAllByUsuarioAndData(usuario, data).stream()
                .map(this::paraDto)
                .collect(Collectors.toList());
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

    private RegistroUsoAplicativoResponseDto paraDto(RegistroUsoAplicativoEntity registro) {
        return new RegistroUsoAplicativoResponseDto(
                registro.getId(),
                registro.getData(),
                registro.getTempoUsoSegundos(),
                registro.getAplicativo().getPacote()
        );
    }
}