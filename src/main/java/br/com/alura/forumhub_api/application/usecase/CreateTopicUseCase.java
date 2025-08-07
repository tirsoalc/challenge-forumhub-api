package br.com.alura.forumhub_api.application.usecase;

import br.com.alura.forumhub_api.api.dto.request.CreateTopicRequest;
import br.com.alura.forumhub_api.domain.entity.Course;
import br.com.alura.forumhub_api.domain.entity.Topic;
import br.com.alura.forumhub_api.domain.entity.User;
import br.com.alura.forumhub_api.domain.repository.CourseRepository;
import br.com.alura.forumhub_api.domain.repository.TopicRepository;
import br.com.alura.forumhub_api.domain.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CreateTopicUseCase {
    
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    
    public CreateTopicUseCase(TopicRepository topicRepository, 
                             UserRepository userRepository, 
                             CourseRepository courseRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }
    
    public Topic execute(CreateTopicRequest request) {
        validateTopicDoesNotExist(request.title(), request.message());
        User author = getAuthenticatedUser();
        Course course = findCourseById(request.courseId());
        
        Topic topic = new Topic(request.title(), request.message(), author, course);
        return topicRepository.save(topic);
    }
    
    private void validateTopicDoesNotExist(String title, String message) {
        if (topicRepository.existsByTitleAndMessage(title, message)) {
            throw new IllegalArgumentException("Já existe um tópico com o mesmo título e mensagem");
        }
    }
    
    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        
        return userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("Usuário autenticado não encontrado"));
    }
    
    private Course findCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
    }
}