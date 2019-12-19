package com.example.demo.modules.controller;

import com.example.demo.common.R;
import com.example.demo.modules.entity.User;
import com.example.demo.modules.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserInfoController {

    @Autowired
    private IUserService userService;

    @GetMapping(params = "info")
    public R getUserInfo() {
        List<User> data = userService.list(null);

        return R.ok(data, "查询成功");
    }
}
