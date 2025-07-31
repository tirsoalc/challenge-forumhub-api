package br.com.alura.forumhub_api.application.usecase;

import br.com.alura.forumhub_api.api.dto.request.UpdateTopicRequest;
import br.com.alura.forumhub_api.domain.entity.Topic;
import br.com.alura.forumhub_api.domain.repository.TopicRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateTopicUseCase {
    
    private final TopicRepository topicRepository;
    
    public UpdateTopicUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
    
    public Topic execute(Long id, UpdateTopicRequest request) {
        Topic topic = findTopicById(id);
        validateNoDuplicateExists(request.title(), request.message(), id);
        
        topic.updateContent(request.title(), request.message());
        return topicRepository.save(topic);
    }
    
    private Topic findTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tópico não encontrado"));
    }
    
    private void validateNoDuplicateExists(String title, String message, Long excludeId) {
        if (topicRepository.existsByTitleAndMessageAndIdNotEquals(title, message, excludeId)) {
            throw new IllegalArgumentException("Já existe um tópico com o mesmo título e mensagem");
        }
    }
}