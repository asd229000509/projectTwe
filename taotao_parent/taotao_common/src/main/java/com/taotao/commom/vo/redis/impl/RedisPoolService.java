package com.taotao.commom.vo.redis.impl;

import com.taotao.commom.vo.redis.RedisFunction;
import com.taotao.commom.vo.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisPoolService implements RedisService {
    @Autowired
    private JedisPool jedisPool;

    private <T> T execute(RedisFunction<T, Jedis> fun) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return fun.callback(jedis);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @Override
    public String set(final String key, final String value) {
        return execute(new RedisFunction<String, Jedis>() {
            @Override
            public String callback(Jedis jedis) {
                return jedis.set(key, value);
            }
        });
    }

    @Override
    public String setex(final String key, final int seconds, final String value) {
        return execute(new RedisFunction<String, Jedis>() {
            @Override
            public String callback(Jedis jedis) {
                return jedis.setex(key,seconds,value);
            }
        });
    }

    @Override
    public Long expire(final String key, final int seconds) {
        return execute(new RedisFunction<Long, Jedis>() {
            @Override
            public Long callback(Jedis jedis) {
                return jedis.expire(key, seconds);
            }
        });
    }

    @Override
    public String get(final String key) {
        return execute(new RedisFunction<String, Jedis>() {
            @Override
            public String callback(Jedis jedis) {
                return jedis.get(key);
            }
        });
    }

    @Override
    public Long del(final String key) {
        return execute(new RedisFunction<Long, Jedis>() {
            @Override
            public Long callback(Jedis jedis) {
                return jedis.del(key);
            }
        });
    }

    @Override
    public Long incr(final String key) {
        return execute(new RedisFunction<Long, Jedis>() {
            @Override
            public Long callback(Jedis jedis) {
                return jedis.incr(key);
            }
        });
    }
}
