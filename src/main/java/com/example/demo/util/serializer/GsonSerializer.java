package com.example.demo.util.serializer;

import com.google.gson.Gson;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import reactor.util.annotation.Nullable;

import java.nio.charset.StandardCharsets;

/**
 * 无法序列化transient属性，有待改进
 */
public class GsonSerializer implements RedisSerializer<Object> {

    private Gson gson = new Gson();

    @Override
    public byte[] serialize(@Nullable Object o) throws SerializationException {
        if(o == null) { return null; }

        String jsonString = gson.toJson(o);
        String className = o.getClass().getName();
        return (className + ":" + jsonString).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Object deserialize(@Nullable byte[] bytes) throws SerializationException {
        if(bytes == null) return null;
        String jsonString = new String(bytes,StandardCharsets.UTF_8);
        String[] arr = jsonString.split(":");
        Class c = null;
        try {
            c = Class.forName(arr[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return gson.fromJson(arr[1],c);
    }
}
