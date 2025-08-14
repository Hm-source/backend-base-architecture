package org.example.basic.repository.user;

import org.example.basic.repository.user.entity.Allocated;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocatedRepository extends JpaRepository<Allocated, Integer> {

    void deleteById(Integer id);
}
