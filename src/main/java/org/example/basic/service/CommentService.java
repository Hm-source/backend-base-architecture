package org.example.basic.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.basic.controller.post.dto.CommentCreateRequestDto;
import org.example.basic.controller.post.dto.CommentResponseDto;
import org.example.basic.repository.post.CommentRepository;
import org.example.basic.repository.post.PostRepository;
import org.example.basic.repository.post.entity.Comment;
import org.example.basic.repository.post.entity.Post;
import org.example.basic.repository.user.UserRepository;
import org.example.basic.repository.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto findById(Integer id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("해당 댓글은 데이터베이스 내 존재하지 않습니다. 코멘트 id : " + id));
        return CommentResponseDto.from(comment);
    }

    @Transactional
    public List<CommentResponseDto> findAll() {
        return commentRepository.findAll()
            .stream()
            .map(CommentResponseDto::from)
            .toList();
    }

    @Transactional
    public CommentResponseDto save(CommentCreateRequestDto requestDto) {
        Integer postId = requestDto.getPostId();
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("포스트가 데이터베이스 내 존재하지 않습니다. 포스트 id : " + postId));
        Integer userId = requestDto.getUserId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("유저가 데이터베이스 내 존재하지 않습니다. 유저 id : " + userId));
        Comment comment = Comment.create(
            requestDto.getContent(),
            post,
            user
        );
        Comment created = commentRepository.save(comment);
        return CommentResponseDto.from(created);
    }

    @Transactional
    public void delete(Integer postId, Integer commentId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("포스트가 데이터베이스 내 존재하지 않습니다. 포스트 id : " + postId));
        Comment found = post.getComments().stream()
            .filter((comment) -> commentId.equals(comment.getId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException(
                "포스트 내 해당 댓글이 존재하지 않습니다. 포스트 id : " + postId + " - 댓글 id: " + commentId));
        post.getComments().remove(found);
        /*
        JPA에서 양방향 연관관계 + Cascade + orphanRemoval이 설정되어 있으면,
        컬렉션에서 제거된 엔티티는 자동으로 DB에서도 DELETE됩니다.
        즉, CommentRepository.deleteById()를 직접 호출할 필요가 없습니다.
         */
    }

}
