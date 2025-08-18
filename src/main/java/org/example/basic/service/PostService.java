package org.example.basic.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.basic.controller.post.dto.PostCreateRequestDto;
import org.example.basic.controller.post.dto.PostResponseDto;
import org.example.basic.repository.post.PostRepository;
import org.example.basic.repository.post.entity.Post;
import org.example.basic.repository.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponseDto findById(Integer id) {
        Post post = postRepository.findById(id) // 없으면 예외, 있으면 엔티티로 반환
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "게시글이 데이터베이스 내 존재하지 않습니다. 게시글 id : " + id));
        return PostResponseDto.from(post);
    }

    @Transactional
    public List<PostResponseDto> findAll() {
        return postRepository.findAll()
            .stream()
            .map(PostResponseDto::from)
            .toList();
    }

    @Transactional
    public PostResponseDto save(PostCreateRequestDto requestDto) {
        Post post = Post.create(
            requestDto.getTitle(),
            requestDto.getContent(),
            userRepository.findById(requestDto.getUserId())
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "해당하는 유저가 없습니다. 유저 id : " + requestDto.getUserId()))
        );
        Post created = postRepository.save(post);
        return PostResponseDto.from(created);
    }

    @Transactional
    public void delete(Integer id) {
        postRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "게시글이 데이터베이스 내 존재하지 않습니다. 게시글 id : " + id));
        postRepository.deleteById(id);
    }
}
