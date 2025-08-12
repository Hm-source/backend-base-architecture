package org.example.basic.controller.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserCreateRequestDto {

    private String username;
    private String password;
    private String name;
    private Integer age;
    private String job;
    private String specialty;
}
