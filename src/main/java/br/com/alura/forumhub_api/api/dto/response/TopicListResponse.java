package br.com.alura.forumhub_api.api.dto.response;

import br.com.alura.forumhub_api.domain.entity.Topic;
import br.com.alura.forumhub_api.domain.enums.Status;

import java.time.LocalDateTime;

public record TopicListResponse(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt,
        Status status,
        String authorName,
        String courseName
) {
    public static TopicListResponse fromDomain(Topic topic) {
        return new TopicListResponse(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt(),
                topic.getStatus(),
                topic.getAuthor().getName(),
                topic.getCourse().getName()
        );
    }
}