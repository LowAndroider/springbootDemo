package com.example.demo.modules.service.impl;

import com.example.demo.modules.dao.UserDao;
import com.example.demo.modules.entity.User;
import com.example.demo.modules.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User insert(User user) {
        return userDao.save(user);
    }

    @Override
    public User del() {
        return null;
    }

    @Override
    public User update() {
        return null;
    }

    @Override
    public User getUserById(String id) {
        return userDao.findUserById(id);
    }

    @Override
    public User getUserByUserName(String username) {
        return userDao.findUserByName(username);
    }

    @Override
    public User check() {
        return null;
    }
}
