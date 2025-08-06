package br.com.alura.forumhub_api.application.service;

import br.com.alura.forumhub_api.domain.entity.Topic;
import br.com.alura.forumhub_api.domain.repository.TopicRepository;
import br.com.alura.forumhub_api.infra.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class TopicSecurityService {

    private final TopicRepository topicRepository;

    public TopicSecurityService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public boolean canEditTopic(Long topicId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        if (hasAdminRole(authentication)) {
            return true;
        }

        if (hasUserRole(authentication)) {
            return isOwnerOfTopic(authentication, topicId);
        }

        return false;
    }

    private boolean hasAdminRole(Authentication authentication) {
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    private boolean hasUserRole(Authentication authentication) {
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"));
    }

    private boolean isOwnerOfTopic(Authentication authentication, Long topicId) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String currentUserEmail = userPrincipal.getUsername();

        Topic topic = topicRepository.findById(topicId).orElse(null);
        if (topic == null) {
            return false;
        }

        return topic.getAuthor().getEmail().equals(currentUserEmail);
    }
}