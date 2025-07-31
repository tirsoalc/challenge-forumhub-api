package br.com.alura.forumhub_api.application.usecase;

import br.com.alura.forumhub_api.domain.entity.Topic;
import br.com.alura.forumhub_api.domain.repository.TopicRepository;
import org.springframework.stereotype.Service;

@Service
public class GetTopicByIdUseCase {
    
    private final TopicRepository topicRepository;
    
    public GetTopicByIdUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
    
    public Topic execute(Long id) {
        return findTopicById(id);
    }
    
    private Topic findTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado"));
    }
}