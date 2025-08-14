package org.example.basic.controller.user.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupResponseDto {

    private Integer id;
    private String name;
    private String desc;
    private List<UserSimpleResponseDto> users;
}