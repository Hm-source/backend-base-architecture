package org.example.basic.repository.post;

import java.util.List;
import java.util.Optional;
import org.example.basic.repository.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Optional<Post> findById(Integer id);

    List<Post> findAll();

    Post save(Post entity);

    void deleteById(Integer id);
}
