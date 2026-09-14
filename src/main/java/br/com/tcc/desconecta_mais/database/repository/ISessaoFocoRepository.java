package br.com.tcc.desconecta_mais.database.repository;

import br.com.tcc.desconecta_mais.database.entity.SessaoFocoEntity;
import br.com.tcc.desconecta_mais.database.entity.UsuarioEntity;
import br.com.tcc.desconecta_mais.enums.StatusSessaoFocoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ISessaoFocoRepository extends JpaRepository<SessaoFocoEntity, Long> {
    // Busca por ID + usuário juntos - garante que a sessão pertence a quem está pedindo
    Optional<SessaoFocoEntity> findByIdAndUsuario(Long id, UsuarioEntity usuario);

    Optional<SessaoFocoEntity> findFirstByUsuarioAndStatusOrderByDataInicioDesc(
            UsuarioEntity usuario, StatusSessaoFocoEnum status);
}