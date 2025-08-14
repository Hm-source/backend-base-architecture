package org.example.basic.controller.user.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.basic.repository.user.entity.Allocated;
import org.example.basic.repository.user.entity.Group;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupResponseDto {

    private Integer id;
    private String name;
    private String desc;
    private List<UserSimpleResponseDto> users;

    public static GroupResponseDto from(Group entity) {
        return new GroupResponseDto(
            entity.getId(),
            entity.getName(),
            entity.getDesc(),
            entity.getAllocates()
                .stream()
                .map(Allocated::getUser)
                .map(UserSimpleResponseDto::from)
                .toList()
        );
    }
}