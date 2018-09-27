package com.example.demo.sys.auth;



import com.example.demo.modules.entity.User;
import com.example.demo.modules.service.IUserService;
import com.example.demo.modules.service.impl.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    private static String toMd5(String src, String salt) {
        return new Md5Hash(src,salt).toString();
    }

    private Map<String,String> userMap = new HashMap<>();

    {
        userMap.put("Joke",toMd5("123123","Joke"));
        setName("customRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        Set<String> roles = getRolesByUserName(userName);
        Set<String> permissions = getPermByUsername(userName);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo(roles);
        authorizationInfo.addStringPermissions(permissions);

        return authorizationInfo;
    }

    private Set<String> getPermByUsername(String userName) {
        Set<String> sets = new HashSet<>();
        sets.add("user:delete");
        return sets;
    }

    private Set<String> getRolesByUserName(String userName) {
        Set<String> sets = new HashSet<>();
        sets.add("admin");
//        sets.add("user");
        return sets;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 获得用户名
        String userName = (String) token.getPrincipal();
        User user = userService.getUserByUserName(userName);
        if(user != null ) {

        }

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userName,"" ,"customRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("Joke"));

        return authenticationInfo;
    }

    private String getPwdByUserName(String userName) {
        return userMap.get(userName);
    }
}

;