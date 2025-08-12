package org.example.basic.repository.user;

import java.util.List;
import org.example.basic.repository.user.entity.User;

public interface IUserRepositoryCustom {

    List<User> findByName(String name);

}
