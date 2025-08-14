package org.example.basic.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.basic.controller.post.dto.CommentCreateRequestDto;
import org.example.basic.controller.post.dto.CommentResponseDto;
import org.example.basic.repository.post.CommentRepository;
import org.example.basic.repository.post.PostRepository;
import org.example.basic.repository.post.entity.Comment;
import org.example.basic.repository.post.entity.Post;
import org.example.basic.repository.user.UserRepository;
import org.example.basic.repository.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CommentService {

    CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDto findById(Integer id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("데이터베이스에 해당 댓글이 존재하지 않습니다. id : " + id));
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
        Integer userId = requestDto.getUserId();
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new RuntimeException("데이터베이스에 해당 게시글이 존재하지 않습니다. id : " + postId));
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("데이터베이스에 해당 유저가 존재하지 않습니다. id : " + userId));
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
            .orElseThrow(() -> new RuntimeException("데이터베이스에 해당 게시글이 존재하지 않습니다. id : " + postId));
        Comment foundComment = post.getComments()
            .stream()
            .filter((comment) -> commentId.equals(comment.getId()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("게시글에 해당 댓글이 존재하지 않습니다. id : " + commentId));
        post.getComments().remove(foundComment);
    }

}
