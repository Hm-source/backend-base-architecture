package org.example.basic.controller.user;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.basic.controller.user.dto.UserCreateRequestDto;
import org.example.basic.controller.user.dto.UserResponseDto;
import org.example.basic.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
/*
위 애너테이션 덕분에 각 필드 앞에 private final 안 써도 됨.
Lombok이 컴파일 시점에 자동으로 붙여줌.
 */
public class UserController {

    UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> user(@PathVariable Integer id) {
        UserResponseDto user = userService.findById(id);
        return ResponseEntity.ok(user);
    }
//
//    @GetMapping("")
//    public ResponseEntity<List<UserResponseDto>> users(
//        @RequestParam(required = false) String username) {
//        List<UserResponseDto> users = Optional.ofNullable(username)
//            .map(userService::findByUsername)
//            .orElseGet(userService::findAll);
//        return ResponseEntity.ok(users);
//    }
    /*
    ofNullable()
    value가 null이 아니면 → Optional.of(value)와 동일 (값을 담은 Optional)
    value가 null이면 → Optional.empty() 반환 (비어 있는 Optional)
     */

    @PostMapping("")
    public ResponseEntity<UserResponseDto> create(@RequestBody UserCreateRequestDto requestDto) {
        UserResponseDto userResponseDto = userService.save(requestDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
