package org.example.basic.controller.user;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.basic.controller.user.dto.GroupCreateRequestDto;
import org.example.basic.controller.user.dto.GroupResponseDto;
import org.example.basic.controller.user.dto.GroupUpdateRequestDto;
import org.example.basic.service.GroupService;
import org.example.basic.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GroupController {

    UserService userService;
    GroupService groupService;

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDto> group(@PathVariable Integer id) {
        GroupResponseDto groupResponseDto = groupService.findById(id);
        return ResponseEntity.ok(groupResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<List<GroupResponseDto>> groups() {
        List<GroupResponseDto> groups = groupService.findAll();
        return ResponseEntity.ok(groups);
    }

    @PostMapping("")
    public ResponseEntity<GroupResponseDto> create(@RequestBody GroupCreateRequestDto requestDto) {
        GroupResponseDto groupResponseDto = groupService.save(requestDto);
        return ResponseEntity.ok(groupResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDto> update(@PathVariable Integer id,
        @RequestBody GroupUpdateRequestDto requestDto) {
        GroupResponseDto groupResponseDto = groupService.update(id, requestDto);
        return ResponseEntity.ok(groupResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        groupService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
