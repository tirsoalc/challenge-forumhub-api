package br.com.alura.forumhub_api.application.usecase;

import br.com.alura.forumhub_api.domain.entity.Topic;
import br.com.alura.forumhub_api.domain.repository.TopicRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListTopicsUseCase {
    
    private final TopicRepository topicRepository;
    
    public ListTopicsUseCase(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }
    
    public List<Topic> execute() {
        return topicRepository.findAllOrderByCreatedAtAsc();
    }
    
    public List<Topic> executeWithLimit(int limit) {
        return topicRepository.findAllOrderByCreatedAtAsc()
                .stream()
                .limit(limit)
                .toList();
    }
    
    public List<Topic> executeByCourseAndYear(String courseName, int year) {
        return topicRepository.findByCourseNameAndYear(courseName, year);
    }
}