package br.com.alura.forumhub_api.domain.repository;

import br.com.alura.forumhub_api.domain.entity.Topic;
import br.com.alura.forumhub_api.domain.enums.Status;

import java.util.List;
import java.util.Optional;

public interface TopicRepository {
    Topic save(Topic topic);
    Optional<Topic> findById(Long id);
    List<Topic> findAll();
    List<Topic> findAllOrderByCreatedAtAsc();
    List<Topic> findByStatus(Status status);
    List<Topic> findByCourseNameAndYear(String courseName, int year);
    void deleteById(Long id);
    boolean existsByTitleAndMessage(String title, String message);
    boolean existsByTitleAndMessageAndIdNotEquals(String title, String message, Long id);
}