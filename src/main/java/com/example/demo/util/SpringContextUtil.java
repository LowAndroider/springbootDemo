package com.example.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Djh
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(SpringContextUtil.class);

    private static ApplicationContext applicationContext;

    /**
     * 获取上下文
     * @return applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.info("applicationContext正在初始化,application:" + applicationContext);
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 通过名字获取上下文中的bean
     * @param name 名称
     * @return  实例化的bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T  getBean(String name){
        assertApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 通过类型获取上下文中的bean
     * @param requiredType 类型
     * @return  实例化的bean
     */
    public static  <T> T getBean(Class<T> requiredType){
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    private static void assertApplicationContext() {
        if (applicationContext == null) {
            throw new RuntimeException("applicationContext属性为null,请检查是否注入了SpringContextHolder!");
        }
    }

}