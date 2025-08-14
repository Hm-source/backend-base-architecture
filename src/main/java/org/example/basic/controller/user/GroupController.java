package org.example.basic.controller.user;

import com.example.demo.controller.user.dto.*;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDto> group(@PathVariable Integer id) {
    }

    @GetMapping("")
    public ResponseEntity<List<GroupResponseDto>> groups() {
    }

    @PostMapping("")
    public ResponseEntity<GroupResponseDto> create(@RequestBody GroupCreateRequestDto request) {
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDto> update(@PathVariable Integer id,
        @RequestBody GroupUpdateRequestDto request) {
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
    }
}
