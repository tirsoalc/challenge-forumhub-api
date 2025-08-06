package br.com.alura.forumhub_api.infra.persistence.entity;

import br.com.alura.forumhub_api.domain.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<RoleJpaEntity> roles = new ArrayList<>();

    public UserJpaEntity(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = new ArrayList<>();
    }

    public UserJpaEntity(Long id, String name, String email, String password, List<RoleJpaEntity> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles != null ? new ArrayList<>(roles) : new ArrayList<>();
    }

    public User toDomain() {
        return new User(id, name, email, password, 
            roles.stream().map(RoleJpaEntity::toDomain).collect(Collectors.toList()));
    }

    public static UserJpaEntity fromDomain(User user) {
        UserJpaEntity userJpa = new UserJpaEntity(user.getId(), user.getName(), user.getEmail(), user.getPassword(), new ArrayList<>());
        if (user.getRoles() != null) {
            userJpa.roles = user.getRoles().stream()
                .map(RoleJpaEntity::fromDomain)
                .collect(Collectors.toList());
        }
        return userJpa;
    }
}