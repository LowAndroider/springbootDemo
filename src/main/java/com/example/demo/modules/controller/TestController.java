package com.example.demo.modules.controller;

import com.example.demo.common.R;
import com.example.demo.modules.entity.User;
import com.example.demo.modules.service.IUserService;
import com.example.demo.sys.auth.CustomRealm;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private IUserService userService;

    @PostMapping(params = "addUser")
    public R addUser(User user) {

        if (user == null || user.getName() == null || user.getPassword() == null) {
            return R.error(-1, "缺少参数");
        }

        String pattern = "%|-|,|。|=|\\*|—|_|\\?|？|\\\\|\\{|}|[|]|\\$|\\+|\\^";

        if (Pattern.matches(pattern, user.getName())) {
            return R.error(-2, "用户名中包含非法字符");
        }

        String pwd = user.getPassword();
        user.setPassword(CustomRealm.toMd5(pwd));
        try {
            User result = userService.insert(user);
            if (result.equals(user)) {
                return R.ok(user);
            } else {
                return R.error();
            }
        } catch (DataIntegrityViolationException e) {
            return R.error("用户名已存在");
        }
    }
}
