package org.example.basic.controller.post;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.basic.controller.post.dto.CommentCreateRequestDto;
import org.example.basic.controller.post.dto.CommentResponseDto;
import org.example.basic.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/comments")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentController {

    private final CommentService commentService;

    @PostMapping("")
    public ResponseEntity<CommentResponseDto> create(
        @RequestBody CommentCreateRequestDto requestDto) {
        CommentResponseDto commentResponseDto = commentService.save(requestDto);
        return ResponseEntity.ok(commentResponseDto);
    }
}
