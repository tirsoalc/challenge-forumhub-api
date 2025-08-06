package br.com.alura.forumhub_api.domain.repository;

import br.com.alura.forumhub_api.domain.entity.Role;

import java.util.Optional;

public interface RoleRepository {
    Optional<Role> findByName(String name);
    Role save(Role role);
}