package com.example.demo.modules.service;

import com.example.demo.modules.entity.User;

public interface IUserService {

    User insert(User user);

    User del();

    User update();

    User getUserById(String id);

    User getUserByUserName(String username);

    User check();
}
