package com.example.demo.config.aop;

import com.example.demo.config.datasource.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 一般而言主库用来写，从库来读
 * 特殊情况下主库也能读
 * @author Djh
 */
@Aspect
@Component
public class DataSourceAop {

    @Pointcut("!@annotation(com.example.demo.config.annotation.Master)" +
            "&& (execution(* com.example.demo.modules.service..*.select*(..)) " +
            "|| execution(* com.example.demo.modules.service..*.get*(..))" +
            "|| execution(* com.example.demo.modules.service..*.list*(..))" +
            "|| execution(* com.example.demo.modules.service..*.page*(..)))")
    public void readPointcut() {}

    @Pointcut("execution(* com.example.demo.modules.service..*.*(..))" +
            "&& (@annotation(com.example.demo.config.annotation.Master)"+
            "|| !(execution(* com.example.demo.modules.service..*.select*(..)) " +
            "|| execution(* com.example.demo.modules.service..*.get*(..))" +
            "|| execution(* com.example.demo.modules.service..*.list*(..))" +
            "|| execution(* com.example.demo.modules.service..*.page*(..))))")
    public void writePointcut() {}

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }
}
