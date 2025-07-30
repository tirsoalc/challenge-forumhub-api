package br.com.alura.forumhub_api.api.dto.response;

import br.com.alura.forumhub_api.domain.entity.Topic;
import br.com.alura.forumhub_api.domain.enums.Status;

import java.time.LocalDateTime;

public record TopicResponse(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Status status,
        UserResponse author,
        CourseResponse course
) {
    public static TopicResponse fromDomain(Topic topic) {
        return new TopicResponse(
                topic.getId(),
                topic.getTitle(),
                topic.getMessage(),
                topic.getCreatedAt(),
                topic.getUpdatedAt(),
                topic.getStatus(),
                UserResponse.fromDomain(topic.getAuthor()),
                CourseResponse.fromDomain(topic.getCourse())
        );
    }
}