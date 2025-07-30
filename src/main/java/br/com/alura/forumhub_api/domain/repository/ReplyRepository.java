package br.com.alura.forumhub_api.domain.repository;

import br.com.alura.forumhub_api.domain.entity.Reply;

import java.util.List;
import java.util.Optional;

public interface ReplyRepository {
    Reply save(Reply reply);
    Optional<Reply> findById(Long id);
    List<Reply> findByTopicId(Long topicId);
    List<Reply> findByTopicIdOrderByCreatedAtAsc(Long topicId);
    void deleteById(Long id);
}