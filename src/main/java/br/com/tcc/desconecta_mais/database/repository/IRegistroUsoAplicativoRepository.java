package br.com.tcc.desconecta_mais.database.repository;

import br.com.tcc.desconecta_mais.database.entity.AplicativoEntity;
import br.com.tcc.desconecta_mais.database.entity.RegistroUsoAplicativoEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IRegistroUsoAplicativoRepository extends JpaRepository<RegistroUsoAplicativoEntity, Long> {

    Optional<RegistroUsoAplicativoEntity> findByUsuarioAndAplicativoAndData(UsuarioEntity usuario, AplicativoEntity aplicativo, LocalDate data);

    List<RegistroUsoAplicativoEntity> findAllByUsuarioAndData(UsuarioEntity usuario, LocalDate data);
}