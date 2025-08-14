package org.example.basic.repository.user;

import java.util.List;
import java.util.Optional;
import org.example.basic.repository.user.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {

    Optional<Group> findById(Integer id);

    List<Group> findAll();

    Group save(Group entity);

    void deleteById(Integer id);
}
