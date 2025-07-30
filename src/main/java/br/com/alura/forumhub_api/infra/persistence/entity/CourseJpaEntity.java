package br.com.alura.forumhub_api.infra.persistence.entity;

import br.com.alura.forumhub_api.domain.entity.Course;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CourseJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String category;

    public CourseJpaEntity(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Course toDomain() {
        return new Course(id, name, category);
    }

    public static CourseJpaEntity fromDomain(Course course) {
        return new CourseJpaEntity(course.getId(), course.getName(), course.getCategory());
    }
}