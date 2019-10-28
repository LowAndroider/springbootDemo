package com.example.demo.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.modules.entity.User;

public interface UserMapper extends BaseMapper<User> {

    int test();
}
