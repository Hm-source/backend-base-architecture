package org.example.basic.controller.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.basic.repository.post.entity.Comment;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Integer id;
    private String content;

    public static CommentResponseDto from(Comment entity) {
        return new CommentResponseDto(
            entity.getId(),
            entity.getContent()
        );
    }
}
