package com.ddaying.kakaopay.keysystem.support.redis;

import com.ddaying.kakaopay.keysystem.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.util.Objects;

@Slf4j
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, Object> stringRedisTemplate;


    public void set (String key, Object value) {
        stringRedisTemplate.set(key, JsonHelper.toJson(value));
    }

    public <T> T get(String key, Type deserializeType) {
        Object value = stringRedisTemplate.get(key);
        if (Objects.isNull(value)) {
            return null;
        }
        return JsonHelper.fromJson(value.toString(), deserializeType);
    }

    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    public Long increment(String key) {
        Long value = stringRedisTemplate.increment(key);
        log.info("INCREMENT :: key = [{}], value = [{}]", key, value);
        return value;
    }

}
