package com.example.demo.filter;

import com.example.demo.modules.entity.User;
import com.example.demo.modules.service.IUserService;
import com.example.demo.modules.service.impl.UserService;
import com.example.demo.util.SpringContextUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.Subject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoLoginFilter implements Filter {

    /**
     * 封装，不需要过滤的list列表
     */
    protected static List<Pattern> patterns = new ArrayList<>();

    public AutoLoginFilter() {
        patterns.add(Pattern.compile("login.html"));
        patterns.add(Pattern.compile("sublogin"));
    }

    private IUserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        userService = (IUserService) SpringContextUtil.getBean(UserService.class);
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();


        if (!isInclude(url)){
            if(!subject.isAuthenticated() && subject.isRemembered()) {
                String username = (String) subject.getPrincipal();
                User user = userService.getUserByUserName(username);
                UsernamePasswordToken token = new UsernamePasswordToken(username,user.getPassword(),true);
                subject.login(token);
                if(session != null) {
                    ((SimpleSession)subject.getSession()).setId(session.getId());
                }
            }
        }

        try {
            filterChain.doFilter(httpRequest, httpResponse);
        } catch (Exception e) {
            httpResponse.sendRedirect("/login.html");
        }
    }

    @Override
    public void destroy() {

    }

    /**
     * 是否需要过滤
     * @param url
     * @return
     */
    private boolean isInclude(String url) {
        for (Pattern pattern : patterns) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }
}
