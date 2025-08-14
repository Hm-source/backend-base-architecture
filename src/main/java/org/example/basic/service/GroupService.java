package org.example.basic.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.basic.controller.user.dto.GroupCreateRequestDto;
import org.example.basic.controller.user.dto.GroupResponseDto;
import org.example.basic.controller.user.dto.GroupUpdateRequestDto;
import org.example.basic.repository.user.AllocatedRepository;
import org.example.basic.repository.user.GroupRepository;
import org.example.basic.repository.user.UserRepository;
import org.example.basic.repository.user.entity.Allocated;
import org.example.basic.repository.user.entity.Group;
import org.example.basic.repository.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class GroupService {

    GroupRepository groupRepository;
    UserRepository userRepository;
    AllocatedRepository allocatedRepository;

    @Transactional
    public GroupResponseDto findById(Integer id) {
        Group group = groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("그룹이 데이터베이스에 존재하지 않습니다. group id : " + id));
        return GroupResponseDto.from(group);
    }

    @Transactional
    public List<GroupResponseDto> findAll() {
        return groupRepository.findAll()
            .stream()
            .map(GroupResponseDto::from)
            .toList();

    }

    @Transactional
    public GroupResponseDto save(GroupCreateRequestDto requestDto) {
        Group group = Group.create(
            requestDto.getName(),
            requestDto.getDesc()
        );
        Group created = groupRepository.save(group);
        return GroupResponseDto.from(created);
    }

    @Transactional
    public void delete(Integer id) {
        groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("데이터베이스에 해당 그룹이 존재하지 않습니다. id : " + id));
        groupRepository.deleteById(id);
    }

    @Transactional
    public GroupResponseDto update(Integer id, GroupUpdateRequestDto requestDto) {
        Group group = groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("데이터베이스에 해당 그룹이 존재하지 않습니다. id : " + id));

        List<User> users = requestDto.getUserIds().stream()
            .map((userId) -> userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("데이터베이스에 해당 유저가 존재하지 않습니다. id : " + id)))
            .toList();

        List<Allocated> allocates = users.stream()
            .map(user -> Allocated.create(group, user))
            .toList();

        allocatedRepository.saveAll(allocates);
        group.update(requestDto.getName(), requestDto.getDesc());
        return GroupResponseDto.from(group);
    }

}
