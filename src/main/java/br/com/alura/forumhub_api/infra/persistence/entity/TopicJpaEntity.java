package br.com.alura.forumhub_api.infra.persistence.entity;

import br.com.alura.forumhub_api.domain.entity.Topic;
import br.com.alura.forumhub_api.domain.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "topics")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TopicJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String title;
    
    @Column(nullable = false, length = 1000)
    private String message;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserJpaEntity author;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseJpaEntity course;

    public TopicJpaEntity(String title, String message, LocalDateTime createdAt, 
                         LocalDateTime updatedAt, Status status, UserJpaEntity author, 
                         CourseJpaEntity course) {
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.author = author;
        this.course = course;
    }

    public Topic toDomain() {
        return new Topic(id, title, message, createdAt, updatedAt, status, 
                        author.toDomain(), course.toDomain());
    }

    public static TopicJpaEntity fromDomain(Topic topic) {
        return new TopicJpaEntity(
            topic.getId(),
            topic.getTitle(),
            topic.getMessage(),
            topic.getCreatedAt(),
            topic.getUpdatedAt(),
            topic.getStatus(),
            UserJpaEntity.fromDomain(topic.getAuthor()),
            CourseJpaEntity.fromDomain(topic.getCourse())
        );
    }
}