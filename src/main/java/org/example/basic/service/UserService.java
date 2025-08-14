package org.example.basic.service;


import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.basic.controller.user.dto.UserCreateRequestDto;
import org.example.basic.controller.user.dto.UserResponseDto;
import org.example.basic.repository.user.UserRepository;
import org.example.basic.repository.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserService {

    UserRepository userRepository;

    @Transactional
    public UserResponseDto findById(Integer id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("데이터베이스에 해당 유저가 없습니다. user id : " + id));
        return UserResponseDto.from(user);
    }

    @Transactional
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
            .stream()
            .map(UserResponseDto::from)
            .toList();
    }

    @Transactional
    public UserResponseDto save(UserCreateRequestDto requestDto) {
        User user = User.create(
            requestDto.getUsername(),
            requestDto.getPassword(),
            requestDto.getName(),
            requestDto.getAge(),
            requestDto.getJob(),
            requestDto.getSpecialty()
        );

        User created = userRepository.save(user);
        return UserResponseDto.from(created);
    }

    /*
    Error:Failed to start Git process: Cannot run program "/usr/bin/git" (in directory "/Users/khm/Desktop/risingCamp/basic"): error=24, Too many open files
     */
    @Transactional
    public void delete(Integer id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("유저가 데이터베이스에 존재하지 않습니다. id : " + id));
        userRepository.deleteById(id);
    }
}
