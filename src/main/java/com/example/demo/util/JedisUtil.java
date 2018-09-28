package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class JedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public Object set(String key, Object value,int expiredTime) {
        redisTemplate.opsForValue().set(key,value,expiredTime,TimeUnit.SECONDS);
        return value;
    }

    public void set(String key, String[] value) {
        redisTemplate.opsForList().leftPushAll(key,value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }

    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
