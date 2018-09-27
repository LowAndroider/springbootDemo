package com.example.demo.modules.controller;

import com.example.demo.common.R;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

    @PostMapping("/addUser")
    public R addUser() {

        return null;
    }
}
