package br.com.alura.forumhub_api.application.usecase;

import br.com.alura.forumhub_api.domain.entity.Reply;
import br.com.alura.forumhub_api.domain.entity.Topic;
import br.com.alura.forumhub_api.domain.entity.TopicWithReplies;
import br.com.alura.forumhub_api.domain.repository.ReplyRepository;
import br.com.alura.forumhub_api.domain.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetTopicByIdUseCase {

    private final TopicRepository topicRepository;
    private final ReplyRepository replyRepository;

    public GetTopicByIdUseCase(TopicRepository topicRepository, ReplyRepository replyRepository) {
        this.topicRepository = topicRepository;
        this.replyRepository = replyRepository;
    }

    public TopicWithReplies execute(Long id) {
        Topic topic = findTopicById(id);
        List<Reply> replies = replyRepository.findByTopicIdOrderByCreatedAtAsc(id);
        return new TopicWithReplies(topic, replies);
    }

    private Topic findTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado"));
    }
}