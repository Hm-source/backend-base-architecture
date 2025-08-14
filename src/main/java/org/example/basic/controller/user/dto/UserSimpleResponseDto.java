package org.example.basic.controller.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.basic.repository.user.entity.User;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSimpleResponseDto {

    private Integer id;
    private String username;
    private String name;

    public static UserSimpleResponseDto from(User entity) {
        return new UserSimpleResponseDto(
            entity.getId(),
            entity.getUsername(),
            entity.getName()
        );
    }
}