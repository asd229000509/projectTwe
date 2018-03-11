package com.taotao.commom.vo.redis;

import redis.clients.jedis.Jedis;

public interface RedisFunction<T,E> {
    public T callback(E jedis);
}
