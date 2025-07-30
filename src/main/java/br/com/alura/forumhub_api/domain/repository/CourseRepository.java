package br.com.alura.forumhub_api.domain.repository;

import br.com.alura.forumhub_api.domain.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    Course save(Course course);
    Optional<Course> findById(Long id);
    Optional<Course> findByName(String name);
    List<Course> findAll();
    void deleteById(Long id);
    boolean existsByName(String name);
}