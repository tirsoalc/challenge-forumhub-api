package br.com.alura.forumhub_api.api.dto.response;

import br.com.alura.forumhub_api.domain.entity.Reply;

import java.time.LocalDateTime;

public record ReplyResponse(
        Long id,
        String message,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        UserResponse author
) {
    public static ReplyResponse fromDomain(Reply reply) {
        return new ReplyResponse(
                reply.getId(),
                reply.getMessage(),
                reply.getCreatedAt(),
                reply.getUpdatedAt(),
                UserResponse.fromDomain(reply.getAuthor())
        );
    }
}