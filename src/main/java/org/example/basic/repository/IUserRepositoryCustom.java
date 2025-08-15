package org.example.basic.repository;


import java.util.List;
import org.example.basic.repository.entity.User;

public interface IUserRepositoryCustom {

    List<User> findByName(String name);
}
