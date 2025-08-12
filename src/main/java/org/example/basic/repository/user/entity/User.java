package org.example.basic.repository.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String password;
    private String name;
    private Integer age;
    private String job;
    private String specialty;
    private LocalDateTime createdAt;
    // User 삭제했을 때, Allocated에 남아있으면 되는거 아닌가?
    // 삭제 정책”을 어디에 둘지(서비스 vs JPA 전이 vs DB)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Allocated> allocates;

    public static User create(String username, String password, String name, Integer age,
        String job,
        String specialty) {
        return new User(
            null,
            username,
            password,
            name,
            age,
            job,
            specialty,
            LocalDateTime.now(),
            null
        );
    }

}
