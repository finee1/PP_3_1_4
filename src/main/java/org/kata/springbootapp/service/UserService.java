package org.kata.springbootapp.service;



import org.kata.springbootapp.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    List<User> listUsers();
    void delete(int id);
    User findById(int id);
    User loadUserByUsername(String username);
}
