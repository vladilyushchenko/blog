package com.leverx.blog.redis;

import java.util.concurrent.TimeUnit;

public interface RedisService {
    void add(Object key, Object value, Integer timeout, TimeUnit timeUnit);

    Object retrieve(Object key);

    boolean contains(Object key);
}
