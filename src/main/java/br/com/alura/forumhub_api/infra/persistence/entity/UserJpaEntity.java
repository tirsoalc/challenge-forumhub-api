package br.com.alura.forumhub_api.infra.persistence.entity;

import br.com.alura.forumhub_api.domain.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 50)
    private String name;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;

    public UserJpaEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User toDomain() {
        return new User(id, name, email, password);
    }

    public static UserJpaEntity fromDomain(User user) {
        return new UserJpaEntity(user.getId(), user.getName(), user.getEmail(), user.getPassword());
    }
}