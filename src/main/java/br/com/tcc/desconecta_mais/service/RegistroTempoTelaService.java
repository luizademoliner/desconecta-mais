package br.com.tcc.desconecta_mais.service;

import br.com.tcc.desconecta_mais.database.entity.RegistroTempoTelaEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.database.repository.IRegistroTempoTelaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RegistroTempoTelaService {

    private final IRegistroTempoTelaRepository registroTempoTelaRepository;

    public void registrar(UsuarioEntity usuario, LocalDate data, long tempoTotalSegundos) {

        RegistroTempoTelaEntity registro = registroTempoTelaRepository.findByUsuarioAndData(usuario, data)
                .orElseGet(() -> RegistroTempoTelaEntity
                        .builder()
                        .usuario(usuario)
                        .data(data)
                        .build());

        registro.setTempoTotalSegundos(tempoTotalSegundos);
        registroTempoTelaRepository.save(registro);
    }
//busca se já existe registro daquele usuário naquele dia — se existe, atualiza o valor; se não existe, cria um novo.
// Respeitando a constraint de uma linha por usuário/dia.


}
