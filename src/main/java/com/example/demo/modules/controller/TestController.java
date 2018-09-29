package com.example.demo.modules.controller;

import com.example.demo.common.R;
import com.example.demo.modules.entity.User;
import com.example.demo.modules.service.IUserService;
import com.example.demo.sys.auth.CustomRealm;
import com.example.demo.util.JedisUtil;
import com.example.demo.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private IUserService userService;

    @Autowired
    private JedisUtil jedisUtil;

    @PostMapping(params = "addUser")
    public R addUser(User user) {

        if (user == null || user.getName() == null || user.getPassword() == null) {
            return R.error(-1, "缺少参数");
        }

        String pattern = ".*[%|\\-,。=\\*—_\\?？\\\\\\{\\}\\[\\]\\$\\+\\^\\|].*";

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

    /**
     * 登录
     * @return 登录信息
     */
    @PostMapping(params = "login")
    public R login(User user) {
        Subject subject = SecurityUtils.getSubject();
        String tokenStr;
        UsernamePasswordToken token = new UsernamePasswordToken(user.getName(),user.getPassword());
        token.setRememberMe(true);

        try {
            subject.login(token);

            //清除之前的token数据
            userService.delToken(user.getName());

            tokenStr = subject.getSession().getId().toString();
            jedisUtil.set(StringUtil.getTokenKey(user.getName()),tokenStr,31536000);
            jedisUtil.set(StringUtil.getTokenKey(tokenStr),user.getName(),31536000);
        } catch (AuthenticationException e) {
            return R.error(e.getMessage());
        }

        R r = R.ok("登录成功");
        r.put("token",tokenStr);

        return r;
    }
}
