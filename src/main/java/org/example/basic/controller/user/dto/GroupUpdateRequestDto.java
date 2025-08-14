package org.example.basic.controller.user.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupUpdateRequestDto {

    private String name;
    private String desc;
    private List<Integer> userIds;
}