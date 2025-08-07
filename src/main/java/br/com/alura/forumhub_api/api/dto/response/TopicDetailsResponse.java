package br.com.alura.forumhub_api.api.dto.response;

import br.com.alura.forumhub_api.domain.entity.TopicWithReplies;
import br.com.alura.forumhub_api.domain.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

public record TopicDetailsResponse(
        Long id,
        String title,
        String message,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Status status,
        UserResponse author,
        CourseResponse course,
        List<ReplyResponse> replies
) {
    public static TopicDetailsResponse fromDomain(TopicWithReplies topicWithReplies) {
        List<ReplyResponse> replyResponses = topicWithReplies.getReplies() != null ? 
                topicWithReplies.getReplies().stream()
                        .map(ReplyResponse::fromDomain)
                        .toList() : List.of();
                        
        return new TopicDetailsResponse(
                topicWithReplies.getTopic().getId(),
                topicWithReplies.getTopic().getTitle(),
                topicWithReplies.getTopic().getMessage(),
                topicWithReplies.getTopic().getCreatedAt(),
                topicWithReplies.getTopic().getUpdatedAt(),
                topicWithReplies.getTopic().getStatus(),
                UserResponse.fromDomain(topicWithReplies.getTopic().getAuthor()),
                CourseResponse.fromDomain(topicWithReplies.getTopic().getCourse()),
                replyResponses
        );
    }
}