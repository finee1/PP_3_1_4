package org.kata.springbootapp.service;

import org.kata.springbootapp.dao.UserDao;
import org.kata.springbootapp.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService, UserDetailsService {

    private final UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.getListUsers();
    }

    @Transactional
    @Override
    public void delete(int id) {userDao.delete(id);}

    @Transactional(readOnly = true)
    @Override
    public User findById(int id) {return userDao.getUserById(id);}

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.getUserByUsername(username);
    }
}
