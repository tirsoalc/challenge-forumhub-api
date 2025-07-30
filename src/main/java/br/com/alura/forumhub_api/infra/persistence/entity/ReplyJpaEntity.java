package br.com.alura.forumhub_api.infra.persistence.entity;

import br.com.alura.forumhub_api.domain.entity.Reply;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "replies")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class ReplyJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 1000)
    private String message;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserJpaEntity author;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private TopicJpaEntity topic;

    public ReplyJpaEntity(String message, LocalDateTime createdAt, LocalDateTime updatedAt, 
                         UserJpaEntity author, TopicJpaEntity topic) {
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.author = author;
        this.topic = topic;
    }

    public Reply toDomain() {
        return new Reply(id, message, createdAt, updatedAt, 
                        author.toDomain(), topic.toDomain());
    }

    public static ReplyJpaEntity fromDomain(Reply reply) {
        return new ReplyJpaEntity(
            reply.getId(),
            reply.getMessage(),
            reply.getCreatedAt(),
            reply.getUpdatedAt(),
            UserJpaEntity.fromDomain(reply.getAuthor()),
            TopicJpaEntity.fromDomain(reply.getTopic())
        );
    }
}