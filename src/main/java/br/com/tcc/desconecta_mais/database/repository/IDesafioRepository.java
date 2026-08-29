package br.com.tcc.desconecta_mais.database.repository;

import br.com.tcc.desconecta_mais.database.entity.DesafioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDesafioRepository extends JpaRepository<DesafioEntity, Long> {

    Optional<DesafioEntity> findByCodigoAcesso(String codigoAcesso);
}