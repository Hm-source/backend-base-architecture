package org.example.basic.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.basic.repository.user.entity.User;

@Getter
@AllArgsConstructor
public class UserResponseDto {

    private Integer id;
    private String username;
    private String name;
    private String job;
    private String specialty;

    public static UserResponseDto from(User entity) {
        return new UserResponseDto(
            entity.getId(),
            entity.getUsername(),
            entity.getName(),
            entity.getJob(),
            entity.getSpecialty()
        );
    }
}
