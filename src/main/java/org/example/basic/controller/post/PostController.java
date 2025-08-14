package org.example.basic.controller.post;

import com.example.demo.controller.post.dto.PostCreateRequestDto;
import com.example.demo.controller.post.dto.PostResponseDto;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> post(@PathVariable Integer id) {
    }

    @GetMapping("")
    public ResponseEntity<List<PostResponseDto>> posts() {
    }

    @PostMapping("")
    public ResponseEntity<PostResponseDto> create(@RequestBody PostCreateRequestDto request) {
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Void> delete(@PathVariable Integer postId,
        @PathVariable Integer commentId) {
    }
}
