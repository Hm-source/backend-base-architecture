package org.example.basic.service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Validated
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto findById(Integer id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "댓글이 존재하지 않습니다. id=" + id));
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
    public CommentResponseDto save(@Valid CommentCreateRequestDto requestDto) {
        Integer postId = requestDto.getPostId();
        Post post = postRepository.findById(postId)
            .orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "포스트가 존재하지 않습니다. id=" + postId));
        Integer userId = requestDto.getUserId();
        User user = userRepository.findById(userId)
            .orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "유저가 존재하지 않습니다. id=" + userId));
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
            .orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "포스트가 존재하지 않습니다. id=" + postId));
        Comment found = post.getComments().stream()
            .filter((comment) -> commentId.equals(comment.getId()))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND,
                "해당 댓글이 포스트에 없습니다. postId=" + postId + ", commentId=" + commentId));
        post.getComments().remove(found);
        /*
        JPA에서 양방향 연관관계 + Cascade + orphanRemoval이 설정되어 있으면,
        컬렉션에서 제거된 엔티티는 자동으로 DB에서도 DELETE됩니다.
        즉, CommentRepository.deleteById()를 직접 호출할 필요가 없습니다.
         */
    }

}
