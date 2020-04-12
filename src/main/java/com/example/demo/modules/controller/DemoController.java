package com.example.demo.modules.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.R;
import com.example.demo.modules.entity.Dept;
import com.example.demo.modules.entity.User;
import com.example.demo.modules.service.DeptService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Djh
 */
@RestController
@RequestMapping("/dept")
@AllArgsConstructor
public class DemoController {

    private DeptService deptService;

    @PostMapping("test")
    public R test(User user) {
        System.out.println(getClass().getResource("/").getPath());
        return R.ok(JSON.toJSONString(user));
    }

    @GetMapping("list")
    public R list() {
        List<Dept> list = deptService.list();
        return R.ok(list);
    }

    @PostMapping("dept")
    public R save(Dept dept) {
        boolean save = deptService.save(dept);
        if (save) {
            return R.ok();
        } else {
            return R.error("插入失败");
        }
    }
}
