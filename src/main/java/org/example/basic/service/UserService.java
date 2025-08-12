package org.example.basic.service;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.basic.controller.user.dto.UserCreateRequestDto;
import org.example.basic.controller.user.dto.UserResponseDto;
import org.example.basic.repository.user.UserRepository;
import org.example.basic.repository.user.entity.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        this.save(new UserCreateRequestDto("hm", "123", "hyomin", 20, "Developer", "Backend"));
        this.save(new UserCreateRequestDto("sm", "123", "sumin", 30, "Developer", "Backend"));
        this.save(new UserCreateRequestDto("cm", "123", "chulmin", 40, "Developer", "Backend"));
    }

    @Transactional
    public UserResponseDto findById(Integer id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("유저가 데이터베이스 내 존재하지 않습니다. 유저 id : " + id));
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
    현재 매핑에선 User → Allocated 쪽에 전이가 없으므로,
    할당(Allocated)이 남아있으면 삭제 시 FK 제약 위반으로
    DataIntegrityViolationException류가 날 수 있음.
     */
    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
