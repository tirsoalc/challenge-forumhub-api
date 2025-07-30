package br.com.alura.forumhub_api.infra.repository;

import br.com.alura.forumhub_api.domain.entity.Topic;
import br.com.alura.forumhub_api.domain.enums.Status;
import br.com.alura.forumhub_api.domain.repository.TopicRepository;
import br.com.alura.forumhub_api.infra.persistence.entity.TopicJpaEntity;
import br.com.alura.forumhub_api.infra.persistence.jpa.SpringTopicRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TopicRepositoryImpl implements TopicRepository {
    
    private final SpringTopicRepository springRepository;
    
    public TopicRepositoryImpl(SpringTopicRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Topic save(Topic topic) {
        TopicJpaEntity entity = TopicJpaEntity.fromDomain(topic);
        TopicJpaEntity savedEntity = springRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Topic> findById(Long id) {
        return springRepository.findById(id)
                .map(TopicJpaEntity::toDomain);
    }

    @Override
    public List<Topic> findAll() {
        return springRepository.findAll()
                .stream()
                .map(TopicJpaEntity::toDomain)
                .toList();
    }

    @Override
    public List<Topic> findAllOrderByCreatedAtAsc() {
        return springRepository.findAllByOrderByCreatedAtAsc()
                .stream()
                .map(TopicJpaEntity::toDomain)
                .toList();
    }

    @Override
    public List<Topic> findByStatus(Status status) {
        return springRepository.findByStatus(status)
                .stream()
                .map(TopicJpaEntity::toDomain)
                .toList();
    }

    @Override
    public List<Topic> findByCourseNameAndYear(String courseName, int year) {
        return springRepository.findByCourseNameAndYear(courseName, year)
                .stream()
                .map(TopicJpaEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        springRepository.deleteById(id);
    }

    @Override
    public boolean existsByTitleAndMessage(String title, String message) {
        return springRepository.existsByTitleAndMessage(title, message);
    }

    @Override
    public boolean existsByTitleAndMessageAndIdNotEquals(String title, String message, Long id) {
        return springRepository.existsByTitleAndMessageAndIdNot(title, message, id);
    }
}