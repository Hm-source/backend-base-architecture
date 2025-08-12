package org.example.basic.controller.post;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.basic.controller.post.dto.PostCreateRequestDto;
import org.example.basic.controller.post.dto.PostResponseDto;
import org.example.basic.service.CommentService;
import org.example.basic.service.PostService;
import org.example.basic.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class PostController {

    UserService userService;
    PostService postService;
    CommentService commentService;

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> post(@PathVariable Integer id) {
        PostResponseDto postResponseDto = postService.findById(id);
        return ResponseEntity.ok(postResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<List<PostResponseDto>> posts() {
        List<PostResponseDto> postResponseDtos = postService.findAll();
        return ResponseEntity.ok(postResponseDtos);
    }

    @PostMapping("")
    public ResponseEntity<PostResponseDto> create(@RequestBody PostCreateRequestDto requestDto) {
        PostResponseDto postResponseDto = postService.save(requestDto);
        return ResponseEntity.ok(postResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        postService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Integer postId,
        @PathVariable Integer commentId) {
        commentService.delete(postId, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
