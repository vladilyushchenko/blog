package com.leverx.blog.service.impl;

import com.leverx.blog.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<Object, Object> redisTemplate;

    @Override
    public void add(Object key, Object value, Integer timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public Object retrieve(Object key) {
        if (!contains(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean contains(Object key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
