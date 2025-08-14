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

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CommentController {

    CommentService commentService;

    @PostMapping("")
    public ResponseEntity<CommentResponseDto> create(@RequestBody CommentCreateRequestDto request) {
        CommentResponseDto commentResponseDto = commentService.save(request);
        return ResponseEntity.ok(commentResponseDto);
    }
}
