package br.com.alura.forumhub_api.infra.persistence.jpa;

import br.com.alura.forumhub_api.infra.persistence.entity.ReplyJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringReplyRepository extends JpaRepository<ReplyJpaEntity, Long> {
    List<ReplyJpaEntity> findByTopicId(Long topicId);
    List<ReplyJpaEntity> findByTopicIdOrderByCreatedAtAsc(Long topicId);
}