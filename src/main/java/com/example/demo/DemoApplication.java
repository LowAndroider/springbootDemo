package com.example.demo;

import com.example.demo.util.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableAspectJAutoProxy(exposeProxy = true)
public class DemoApplication {

    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(DemoApplication.class, args);
        SpringContextUtil.setApplicationContext(app);
    }
}
