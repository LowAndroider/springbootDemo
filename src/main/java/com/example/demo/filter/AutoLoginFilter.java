package com.example.demo.filter;

import com.example.demo.common.R;
import com.example.demo.modules.entity.User;
import com.example.demo.modules.service.IUserService;
import com.example.demo.modules.service.impl.UserService;
import com.example.demo.util.SpringContextUtil;
import com.google.gson.Gson;
import io.netty.util.internal.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoLoginFilter extends AccessControlFilter {


    /**
     * 封装，不需要过滤的list列表
     */
    private static List<Pattern> patterns = new ArrayList<>();

    public AutoLoginFilter() {
        patterns.add(Pattern.compile("login.html"));
        patterns.add(Pattern.compile("^/test"));
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String tokenParam = httpRequest.getParameter("token");
        if(StringUtil.isNullOrEmpty(tokenParam)) {
            return false;
        }

        IUserService userService = (IUserService) SpringContextUtil.getBean(UserService.class);
        String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        if (url.startsWith("/") && url.length() > 1) {
            url = url.substring(1);
        }

        //改为无状态
//        Subject subject = SecurityUtils.getSubject();
//        Session session = subject.getSession();


        if (!isInclude(url)){


            String username = userService.getUserNameByToken(tokenParam);
            return username != null;
        }

        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
//        httpServletResponse.sendRedirect("/login.html");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setHeader("Content-type","text/html;charset=UTF-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        Gson gson = new Gson();
        printWriter.print(gson.toJson(R.error("缺少token或token错误")));

        return false;
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
