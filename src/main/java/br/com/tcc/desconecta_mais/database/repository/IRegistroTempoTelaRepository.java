package br.com.tcc.desconecta_mais.database.repository;

import br.com.tcc.desconecta_mais.database.entity.RegistroTempoTelaEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IRegistroTempoTelaRepository extends JpaRepository<RegistroTempoTelaEntity, Long> {

    Optional<RegistroTempoTelaEntity> findByUsuarioAndData(UsuarioEntity usuario, LocalDate data);

    List<RegistroTempoTelaEntity> findByUsuarioAndDataBetweenOrderByDataAsc(UsuarioEntity usuario, LocalDate inicio, LocalDate fim);
}
