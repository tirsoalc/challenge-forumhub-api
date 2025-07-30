package br.com.alura.forumhub_api.infra.repository;

import br.com.alura.forumhub_api.domain.entity.Course;
import br.com.alura.forumhub_api.domain.repository.CourseRepository;
import br.com.alura.forumhub_api.infra.persistence.entity.CourseJpaEntity;
import br.com.alura.forumhub_api.infra.persistence.jpa.SpringCourseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepositoryImpl implements CourseRepository {
    
    private final SpringCourseRepository springRepository;
    
    public CourseRepositoryImpl(SpringCourseRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Course save(Course course) {
        CourseJpaEntity entity = CourseJpaEntity.fromDomain(course);
        CourseJpaEntity savedEntity = springRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Course> findById(Long id) {
        return springRepository.findById(id)
                .map(CourseJpaEntity::toDomain);
    }

    @Override
    public Optional<Course> findByName(String name) {
        return springRepository.findByName(name)
                .map(CourseJpaEntity::toDomain);
    }

    @Override
    public List<Course> findAll() {
        return springRepository.findAll()
                .stream()
                .map(CourseJpaEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        springRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return springRepository.existsByName(name);
    }
}