package org.example.basic.service;


import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.basic.controller.dto.UserCreateRequestDto;
import org.example.basic.controller.dto.UserResponseDto;
import org.example.basic.repository.UserRepository;
import org.example.basic.repository.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        this.save(new UserCreateRequestDto("aaron", "123", "Aaron", 10, "DEVELOPER", "Backend"));
        this.save(new UserCreateRequestDto("baron", "123", "Baron", 20, "DEVELOPER", "Frontend"));
        this.save(new UserCreateRequestDto("caron", "123", "Caron", 30, "ENGINEER", "DevOps/SRE"));
    }

    public UserResponseDto findById(Integer id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("유저가 데이터베이스 내 존재하지 않습니다. 유저 id : " + id));
        return UserResponseDto.from(user);
    }

    public List<UserResponseDto> findByUsername(String username) {
        List<User> users = userRepository.findByName(username);
        return users.stream()
            .map(UserResponseDto::from)
            .toList();
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
            .stream()
            .map(UserResponseDto::from)
            .toList();
    }

    public UserResponseDto save(UserCreateRequestDto request) {
        User user = User.create(
            request.getUsername(),
            request.getPassword(),
            request.getName(),
            request.getAge(),
            request.getJob(),
            request.getSpecialty()
        );
        User created = userRepository.save(user);
        return UserResponseDto.from(created);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}