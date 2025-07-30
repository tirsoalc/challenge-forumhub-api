package br.com.alura.forumhub_api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Reply {
    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User author;
    private Topic topic;

    public Reply(String message, User author, Topic topic) {
        this.message = message;
        this.author = author;
        this.topic = topic;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateMessage(String message) {
        if (message != null && !message.trim().isEmpty()) {
            this.message = message;
            this.updatedAt = LocalDateTime.now();
        }
    }
}