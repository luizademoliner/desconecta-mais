package br.com.tcc.desconecta_mais.database.repository;

import br.com.tcc.desconecta_mais.database.entity.SessaoFocoEntity;
import br.com.tcc.desconecta_mais.database.entity.TarefaEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ITarefaRepository extends JpaRepository<TarefaEntity, Long> {
    List<TarefaEntity> findAllBySessaoFocoOrderByDataCriacaoAsc(SessaoFocoEntity sessaoFoco);

    // Navega Tarefa -> SessaoFoco -> Usuario, então só encontra a tarefa
    // se ela pertencer a uma sessão do usuário logado
    Optional<TarefaEntity> findByIdAndSessaoFoco_Usuario(Long id, UsuarioEntity usuario);
}