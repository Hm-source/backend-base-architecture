package org.example.basic.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.basic.repository.entity.QUser;
import org.example.basic.repository.entity.User;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCustomImpl implements IUserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<User> findByName(String name) {
        return jpaQueryFactory.selectFrom(QUser.user)
            .where(QUser.user.name.trim().eq(name))
            .fetch();
    }
}
