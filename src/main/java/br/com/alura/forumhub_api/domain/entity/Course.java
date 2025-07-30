package br.com.alura.forumhub_api.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Course {
    private Long id;
    private String name;
    private String category;

    public Course(String name, String category) {
        this.name = name;
        this.category = category;
    }
}