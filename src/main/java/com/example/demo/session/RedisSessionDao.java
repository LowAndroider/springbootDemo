package com.example.demo.session;

import com.example.demo.util.JedisUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class RedisSessionDao extends AbstractSessionDAO {

    private final Logger logger = LoggerFactory.getLogger(RedisSessionDao.class);

    @Resource
    private JedisUtil jedisUtil;

    private final String shiro_session_prefix = "demo-session:";

    private String getKey(String key) {
        return shiro_session_prefix + key;
    }


    private void saveSession(Session session) {
        if(session != null && session.getId() != null) {
            String key = getKey(session.getId().toString());
            jedisUtil.set(key,session,600);
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        //该序列化的sessionId非原始sessionId
        Serializable sessionId = generateSessionId(session);
        ((SimpleSession)session).setId(sessionId);
        saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        logger.info("read session");
        if(sessionId == null) return null;

        String key = getKey(sessionId.toString());
        SimpleSession session = (SimpleSession) jedisUtil.get(key);
        return session;
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        logger.info("更新session");
        saveSession(session);
    }

    @Override
    public void delete(Session session) {
        if(session == null || session.getId() == null) return;
        String key = getKey(session.getId().toString());
        jedisUtil.del(key);
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<String> keys = jedisUtil.keys(shiro_session_prefix);
        Set<Session> sessions = new HashSet<>();
        if(CollectionUtils.isEmpty(keys)) {
            return sessions;
        }

        for(String key: keys) {
            SimpleSession simpleSession = (SimpleSession) jedisUtil.get(key);
            simpleSession.setId(key);
            sessions.add(simpleSession);
        }

        return sessions;
    }
}
