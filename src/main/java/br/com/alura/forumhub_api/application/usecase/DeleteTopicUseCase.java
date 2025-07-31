package br.com.alura.forumhub_api.application.usecase;

import br.com.alura.forumhub_api.domain.repository.TopicRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteTopicUseCase {
    
    private final TopicRepository topicRepository;
    
    public DeleteTopicUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
    
    public void execute(Long id) {
        validateTopicExists(id);
        topicRepository.deleteById(id);
    }
    
    private void validateTopicExists(Long id) {
        if (!topicRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Tópico não encontrado");
        }
    }
}