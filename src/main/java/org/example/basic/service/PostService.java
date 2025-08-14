package org.example.basic.service;


import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.basic.controller.post.dto.PostCreateRequestDto;
import org.example.basic.controller.post.dto.PostResponseDto;
import org.example.basic.repository.post.PostRepository;
import org.example.basic.repository.post.entity.Post;
import org.example.basic.repository.user.UserRepository;
import org.example.basic.repository.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PostService {

    PostRepository postRepository;
    UserRepository userRepository;

    @Transactional
    public PostResponseDto findById(Integer id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("데이터베이스에 해당 게시글이 존재하지 않습니다. id : " + id));
        return PostResponseDto.from(post);
    }

    @Transactional
    public List<PostResponseDto> findAll() {
        List<PostResponseDto> posts = postRepository.findAll()
            .stream()
            .map(PostResponseDto::from)
            .toList();
        return posts;
    }

    @Transactional
    public PostResponseDto save(PostCreateRequestDto requestDto) {
        Integer userId = requestDto.getUserId();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("데이터베이스에 해당 유저가 존재하지 않습니다. id : " + userId));
        Post post = Post.create(
            requestDto.getTitle(),
            requestDto.getContent(),
            user
        );
        Post created = postRepository.save(post);
        return PostResponseDto.from(created);
    }

    @Transactional
    public void delete(Integer id) {
        postRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("데이터베이스에 해당 게시글이 존재하지 않습니다. id : " + id));
        postRepository.deleteById(id);
    }
}

