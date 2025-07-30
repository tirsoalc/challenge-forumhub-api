package br.com.alura.forumhub_api.api.dto.response;

import br.com.alura.forumhub_api.domain.entity.Course;

public record CourseResponse(
        Long id,
        String name,
        String category
) {
    public static CourseResponse fromDomain(Course course) {
        return new CourseResponse(
                course.getId(),
                course.getName(),
                course.getCategory()
        );
    }
}