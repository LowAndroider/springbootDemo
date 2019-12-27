package com.example.demo.modules.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.modules.dao.UserMapper;
import com.example.demo.modules.entity.User;
import com.example.demo.modules.service.IUserService;
import com.example.demo.util.JedisUtil;
import com.example.demo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private JedisUtil jedisUtil;

    @Resource
    private UserMapper mapper;

    @Override
    public List<User> list(Wrapper<User> condition) {
        return mapper.selectList(condition);
    }

    @Override
    public int insert(User user) {
        return mapper.insert(user);
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
        return mapper.selectById(id);
    }

    @Override
    public User getUserByUserName(String username) {
        User user = new User();
        user.setName(username);
        Wrapper<User> wrapper = new QueryWrapper<>(user);
        return mapper.selectOne(wrapper);
    }

    @Override
    public User check() {
        return null;
    }

    @Override
    public String getSessionId(String username) {
        jedisUtil.keys(toString());
        return StringUtil.value(jedisUtil.get(toKey(username)));
    }

    @Override
    public String getUserNameByToken(String token) {
        return StringUtil.value(jedisUtil.get(toKey(token)));
    }

    @Override
    public void delToken(String username) {
        String tokenKey = (String) jedisUtil.get(toKey(username));
        jedisUtil.del(toKey(username));
        jedisUtil.del(toKey(tokenKey));
    }

    private String toKey(String key) {
        return StringUtil.getTokenKey(key);
    }
}
