package com.example.demo.modules.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.example.demo.modules.entity.User;

import java.util.List;

public interface IUserService {

    List<User> list(Wrapper<User> condition);

    int insert(User user);

    User del();

    User update();

    User getUserById(String id);

    User getUserByUserName(String username);

    User check();

    String getSessionId(String username);

    String getUserNameByToken(String token);

    /**
     * 删除token
     */
    void delToken(String username);
}
