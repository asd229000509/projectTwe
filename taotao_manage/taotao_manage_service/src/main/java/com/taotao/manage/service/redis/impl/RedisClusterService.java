package com.taotao.manage.service.redis.impl;

import com.taotao.manage.service.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

public class RedisClusterService implements RedisService {
    //找得到则注入，也即此对象的注入是非必须的
    @Autowired(required = false)
    private JedisCluster jedisCluster;

    @Override
    public String set(String key, String value) {
        String result = jedisCluster.set(key, value);
        return result;
    }

    @Override
    public String setex(String key, int seconds, String value) {
        String result = jedisCluster.setex(key, seconds, value);
        return result;
    }

    @Override
    public Long expire(String key, int seconds) {
        Long result = jedisCluster.expire(key, seconds);
        return result;
    }

    @Override
    public String get(String key) {
        String result = jedisCluster.get(key);
        return result;
    }

    @Override
    public Long del(String key) {
        Long result = jedisCluster.del(key);
        return result;
    }

    @Override
    public Long incr(String key) {
        Long result = jedisCluster.incr(key);
        return result;
    }
}
