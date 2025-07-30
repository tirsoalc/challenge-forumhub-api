package br.com.alura.forumhub_api.infra.repository;

import br.com.alura.forumhub_api.domain.entity.User;
import br.com.alura.forumhub_api.domain.repository.UserRepository;
import br.com.alura.forumhub_api.infra.persistence.entity.UserJpaEntity;
import br.com.alura.forumhub_api.infra.persistence.jpa.SpringUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private final SpringUserRepository springRepository;
    
    public UserRepositoryImpl(SpringUserRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity entity = UserJpaEntity.fromDomain(user);
        UserJpaEntity savedEntity = springRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<User> findById(Long id) {
        return springRepository.findById(id)
                .map(UserJpaEntity::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springRepository.findByEmail(email)
                .map(UserJpaEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        springRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springRepository.existsByEmail(email);
    }
}