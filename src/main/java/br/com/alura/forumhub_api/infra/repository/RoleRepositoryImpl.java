package br.com.alura.forumhub_api.infra.repository;

import br.com.alura.forumhub_api.domain.entity.Role;
import br.com.alura.forumhub_api.domain.repository.RoleRepository;
import br.com.alura.forumhub_api.infra.persistence.entity.RoleJpaEntity;
import br.com.alura.forumhub_api.infra.persistence.jpa.SpringRoleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final SpringRoleRepository springRepository;

    public RoleRepositoryImpl(SpringRoleRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return springRepository.findByName(name)
                .map(RoleJpaEntity::toDomain);
    }

    @Override
    public Role save(Role role) {
        RoleJpaEntity entity = RoleJpaEntity.fromDomain(role);
        RoleJpaEntity savedEntity = springRepository.save(entity);
        return savedEntity.toDomain();
    }
}