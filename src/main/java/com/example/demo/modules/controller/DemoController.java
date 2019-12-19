package com.example.demo.modules.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.R;
import com.example.demo.modules.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Djh
 */
@RestController
public class DemoController {

    @PostMapping("test")
    public R test(User user) {

        return R.ok(JSON.toJSONString(user));
    }
}
