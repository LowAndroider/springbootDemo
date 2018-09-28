package com.example.demo.modules.controller;

import com.example.demo.common.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserInfoController {

    @GetMapping(params = "info")
    public R getUserInfo() {

        return R.ok("info");
    }
}
