package org.example.basic.repository;

import java.util.List;
import java.util.Optional;
import org.example.basic.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, IUserRepositoryCustom {

    Optional<User> findById(Integer id);

    List<User> findAll();

    User save(User entity);

    void deleteById(Integer id);
}