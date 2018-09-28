package com.example.demo.cache;

import com.example.demo.util.JedisUtil;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.session.Session;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class RedisCache<K,V> implements Cache<K,V> {

    @Autowired
    private JedisUtil jedisUtil;

    private final String cache_prefix = "demo-cache";

    private String getKey(K k) {
        if(k instanceof String) {
            return cache_prefix + k;
        }
        return new String(SerializationUtils.serialize(k));
    }

    @Override
    public V get(K k) throws CacheException {
        Object value = jedisUtil.get(getKey(k));
        if(value != null) {
            return (V) value;
        }

        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException {
        String key = getKey(k);
        Object value = jedisUtil.get(key);
        jedisUtil.set(key,value,600);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        String key = getKey(k);
        Object value =  jedisUtil.get(key);
        jedisUtil.del(key);

        if(value != null) {
            return (V) value;
        }

        return null;
    }

    @Override
    public void clear() throws CacheException {

    }

    @Override
    public int size() {
        Set<String> keys = jedisUtil.keys(cache_prefix);
        if (!CollectionUtils.isEmpty(keys)) {
            return keys.size();
        }
        return 0;
    }

    @Override
    public Set<K> keys() {
        Set<String> keys = jedisUtil.keys(cache_prefix);
        Set<String> ks = new HashSet<>();

        if(CollectionUtils.isEmpty(keys)) {
            return (Set<K>) ks;
        }

        return (Set<K>) keys;
    }

    @Override
    public Collection<V> values() {
        Set<String> keys = jedisUtil.keys(cache_prefix);
        Set<Session> vs = new HashSet<>();

        if(CollectionUtils.isEmpty(keys)) return (Collection<V>) vs;

        for (String key: keys) {
            Session value = (Session) jedisUtil.get(key);
            vs.add(value);
        }

        return (Collection<V>) vs;
    }
}
