package org.example.basic.controller.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostCreateRequestDto {

    @NotBlank
    @Size(max = 20)
    private String title;
    @NotBlank
    @Size(min = 10)
    private String content;
    @NotNull
    private Integer userId;
}
