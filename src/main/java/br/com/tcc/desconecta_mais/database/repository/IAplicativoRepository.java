package br.com.tcc.desconecta_mais.database.repository;

import br.com.tcc.desconecta_mais.database.entity.AplicativoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IAplicativoRepository extends JpaRepository<AplicativoEntity, Long> {
    Optional<AplicativoEntity> findByPacote(String pacote);
}