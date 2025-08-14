package org.example.basic.controller.post;

import com.example.demo.controller.post.dto.CommentCreateRequestDto;
import com.example.demo.controller.post.dto.CommentResponseDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentController {

    @PostMapping("")
    public ResponseEntity<CommentResponseDto> create(@RequestBody CommentCreateRequestDto request) {
    }
}
