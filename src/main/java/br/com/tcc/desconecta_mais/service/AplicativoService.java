package br.com.tcc.desconecta_mais.service;

import br.com.tcc.desconecta_mais.database.entity.AplicativoEntity;
import br.com.tcc.desconecta_mais.database.repository.IAplicativoRepository;
import br.com.tcc.desconecta_mais.dto.AplicativoRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AplicativoService {

    private final IAplicativoRepository repository;

    public AplicativoEntity buscarOuCriar(AplicativoRequestDto dto) {
        return repository.findByPacote(dto.getPacote())
                .orElseGet(() -> repository.save(
                        AplicativoEntity.builder()
                                .nome(dto.getNome())
                                .pacote(dto.getPacote())
                                .build()
                ));
    }
}