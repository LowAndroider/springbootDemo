package com.example.demo.modules.controller;

import com.example.demo.modules.entity.Address;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Djh
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("valid1")
    public void valid1(@Validated(Address.Type1.class) Address address) {}

    @PostMapping("valid2")
    public void valid2(@Validated(Address.Type2.class) Address address) {}
}
