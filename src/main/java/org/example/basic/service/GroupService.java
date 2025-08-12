package org.example.basic.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final AllocatedRepository allocatedRepository;

    @Transactional
    public GroupResponseDto findById(Integer id) {
        Group group = groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("그룹이 데이터베이스 내 존재하지 않습니다. 그룹 id : \" + id"));
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
        Group group = groupRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("그룹이 데이터베이스 내 존재하지 않습니다. 그룹 id : " + id));
        groupRepository.deleteById(id);
    }

    @Transactional
    public GroupResponseDto update(Integer groupId, GroupUpdateRequestDto requestDto) {
        Group group = groupRepository.findById(groupId)
            .orElseThrow(() ->
                new RuntimeException("그룹이 데이터베이스 내 존재하지 않습니다. 그룹 id : " + groupId));
        List<User> users = requestDto.getUserIds().stream()
            .map((id) -> userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저가 데이터베이스 내 존재하지 않습니다. 유저 id : " + id))
            ).toList();
        List<Allocated> allocates = users.stream()
            .map(user -> Allocated.create(group, user))
            .toList();
        allocatedRepository.saveAll(allocates);
        group.update(requestDto.getName(), requestDto.getDesc());
        return GroupResponseDto.from(group);
    }
}
