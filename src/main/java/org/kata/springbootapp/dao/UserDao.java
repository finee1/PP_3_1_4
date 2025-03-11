package org.kata.springbootapp.dao;

import org.kata.springbootapp.model.User;

import java.util.List;

public interface UserDao {
    void add(User user);
    List<User> getListUsers();
    User getUserById(int id);
    User getUserByUsername(String username);
    void delete(int id);
}
