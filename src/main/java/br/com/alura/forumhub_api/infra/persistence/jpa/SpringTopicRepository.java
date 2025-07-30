package br.com.alura.forumhub_api.infra.persistence.jpa;

import br.com.alura.forumhub_api.domain.enums.Status;
import br.com.alura.forumhub_api.infra.persistence.entity.TopicJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SpringTopicRepository extends JpaRepository<TopicJpaEntity, Long> {
    List<TopicJpaEntity> findAllByOrderByCreatedAtAsc();
    List<TopicJpaEntity> findByStatus(Status status);
    boolean existsByTitleAndMessage(String title, String message);
    boolean existsByTitleAndMessageAndIdNot(String title, String message, Long id);
    
    @Query("SELECT t FROM TopicJpaEntity t JOIN t.course c WHERE c.name = :courseName AND YEAR(t.createdAt) = :year")
    List<TopicJpaEntity> findByCourseNameAndYear(String courseName, int year);
}