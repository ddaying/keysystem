package com.ddaying.kakaopay.keysystem.support;


import com.ddaying.kakaopay.keysystem.support.redis.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("memory")
@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private RedisService redisService;

    @Test
    public void increment() {
        String key = "increment-test";
        LongStream.range(0, 100).forEach(n -> {
            redisService.increment(key);
        });

        Long value = redisService.get(key, Long.class);
        assertThat(value).isNotNull();
        assertThat(value).isGreaterThan(1L);

        redisService.delete(key);
    }
}
