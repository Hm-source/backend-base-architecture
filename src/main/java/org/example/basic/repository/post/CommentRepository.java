package org.example.basic.repository.post;

import java.util.List;
import java.util.Optional;
import org.example.basic.repository.post.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Optional<Comment> findById(Integer id);

    List<Comment> findAll();

    Comment save(Comment entity);

    void deleteById(Integer id);
}
