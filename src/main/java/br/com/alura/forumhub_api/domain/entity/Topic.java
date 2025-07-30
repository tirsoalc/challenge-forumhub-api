package br.com.alura.forumhub_api.domain.entity;

import br.com.alura.forumhub_api.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topic {
    private Long id;
    private String title;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
    private User author;
    private Course course;

    public Topic(String title, String message, User author, Course course) {
        this.title = title;
        this.message = message;
        this.author = author;
        this.course = course;
        this.status = Status.ABERTO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateContent(String title, String message) {
        if (title != null && !title.trim().isEmpty()) {
            this.title = title;
        }
        if (message != null && !message.trim().isEmpty()) {
            this.message = message;
        }
        this.updatedAt = LocalDateTime.now();
    }

    public void solve() {
        this.status = Status.SOLUCIONADO;
        this.updatedAt = LocalDateTime.now();
    }

    public void reopen() {
        this.status = Status.ABERTO;
        this.updatedAt = LocalDateTime.now();
    }
}