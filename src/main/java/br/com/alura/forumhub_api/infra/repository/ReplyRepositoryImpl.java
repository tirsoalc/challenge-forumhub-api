package br.com.alura.forumhub_api.infra.repository;

import br.com.alura.forumhub_api.domain.entity.Reply;
import br.com.alura.forumhub_api.domain.repository.ReplyRepository;
import br.com.alura.forumhub_api.infra.persistence.entity.ReplyJpaEntity;
import br.com.alura.forumhub_api.infra.persistence.jpa.SpringReplyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReplyRepositoryImpl implements ReplyRepository {
    
    private final SpringReplyRepository springRepository;
    
    public ReplyRepositoryImpl(SpringReplyRepository springRepository) {
        this.springRepository = springRepository;
    }

    @Override
    public Reply save(Reply reply) {
        ReplyJpaEntity entity = ReplyJpaEntity.fromDomain(reply);
        ReplyJpaEntity savedEntity = springRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Reply> findById(Long id) {
        return springRepository.findById(id)
                .map(ReplyJpaEntity::toDomain);
    }

    @Override
    public List<Reply> findByTopicId(Long topicId) {
        return springRepository.findByTopicId(topicId)
                .stream()
                .map(ReplyJpaEntity::toDomain)
                .toList();
    }

    @Override
    public List<Reply> findByTopicIdOrderByCreatedAtAsc(Long topicId) {
        return springRepository.findByTopicIdOrderByCreatedAtAsc(topicId)
                .stream()
                .map(ReplyJpaEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        springRepository.deleteById(id);
    }
}