package br.com.alura.forumhub_api.infra.persistence.jpa;

import br.com.alura.forumhub_api.infra.persistence.entity.RoleJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringRoleRepository extends JpaRepository<RoleJpaEntity, Long> {
    Optional<RoleJpaEntity> findByName(String name);
}