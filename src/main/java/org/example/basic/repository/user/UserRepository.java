package org.example.basic.repository.user;

import java.util.List;
import java.util.Optional;
import org.example.basic.repository.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(Integer id);

    List<User> findAll();

    User save(User entity);

    void deleteById(Integer id);
}
