package org.example.basic.controller.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.basic.controller.user.dto.UserSimpleResponseDto;
import org.example.basic.repository.post.entity.Post;

@Getter
@AllArgsConstructor
public class PostResponseDto {

    private Integer id;
    private String title;
    private String content;
    private List<CommentResponseDto> comments;
    private UserSimpleResponseDto createdBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private UserSimpleResponseDto updatedBy;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public static PostResponseDto from(Post entity) {
        return new PostResponseDto(
            entity.getId(),
            entity.getTitle(),
            entity.getContent(),
            entity.getComments().stream().map(CommentResponseDto::from).toList(),
            UserSimpleResponseDto.from(entity.getCreatedBy()),
            entity.getCreatedAt(),
            UserSimpleResponseDto.from(entity.getUpdatedBy()),
            entity.getUpdatedAt()
        );
    }
}
