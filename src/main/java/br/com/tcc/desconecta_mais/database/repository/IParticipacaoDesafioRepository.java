package br.com.tcc.desconecta_mais.database.repository;

import br.com.tcc.desconecta_mais.database.entity.DesafioEntity;
import br.com.tcc.desconecta_mais.database.entity.ParticipacaoDesafioEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IParticipacaoDesafioRepository extends JpaRepository<ParticipacaoDesafioEntity, Long> {

    boolean existsByUsuarioAndDesafio(UsuarioEntity usuario, DesafioEntity desafio);

    Optional<ParticipacaoDesafioEntity> findByUsuarioAndDesafioId(UsuarioEntity usuario, Long desafioId);

    List<ParticipacaoDesafioEntity> findAllByUsuario(UsuarioEntity usuario);
}