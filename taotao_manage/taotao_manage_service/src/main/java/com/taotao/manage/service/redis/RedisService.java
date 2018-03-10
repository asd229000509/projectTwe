package com.taotao.manage.service.redis;

public interface RedisService {
    //设置
    String set(String key, String value);

    //设置并同时设置过期时间
    String setex(String key, int seconds, String value);
    // 设置key过期
    public Long expire(String key, int seconds);

    // 获取key值
    public String get(String key);

    // 删除key
    public Long del(String key);

    // 自增
    public Long incr(String key);
}
