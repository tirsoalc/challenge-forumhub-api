package br.com.alura.forumhub_api.infra.persistence.jpa;

import br.com.alura.forumhub_api.infra.persistence.entity.CourseJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringCourseRepository extends JpaRepository<CourseJpaEntity, Long> {
    Optional<CourseJpaEntity> findByName(String name);
    boolean existsByName(String name);
}