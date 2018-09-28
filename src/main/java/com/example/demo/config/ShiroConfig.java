package com.example.demo.config;

import com.example.demo.cache.RedisCacheManager;
import com.example.demo.filter.AutoLoginFilter;
import com.example.demo.session.RedisSessionDao;
import com.example.demo.sys.auth.CustomRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean
    public SessionManager sessionManager(RedisSessionDao redisSessionDao,RedisCacheManager redisCacheManager) {
        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setCacheManager(redisCacheManager);
        sessionManager.setSessionDAO(redisSessionDao);
        return sessionManager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, AutoLoginFilter autoLoginFilter) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        Map<String,String> filterDefinitions = new LinkedHashMap<>();
        Map<String, Filter> filterMap = shiroFilter.getFilters();
        filterMap.put("isLogin",autoLoginFilter);

        filterDefinitions.put("/test*","anon");
        filterDefinitions.put("/login.html","anon");
        filterDefinitions.put("/*","authc");

        shiroFilter.setFilterChainDefinitionMap(filterDefinitions);

        return shiroFilter;
    }

    @Bean("securityManager")
    public SecurityManager securityManager(CustomRealm customRealm, CredentialsMatcher credentialsMatcher, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSessionManager(sessionManager);
        securityManager.setRealm(customRealm);
        customRealm.setCredentialsMatcher(credentialsMatcher);

        return securityManager;
    }

    @Bean("credentialsMather")
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }

    /**
     * 添加注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
