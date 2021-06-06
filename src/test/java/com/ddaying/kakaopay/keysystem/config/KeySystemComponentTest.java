package com.ddaying.kakaopay.keysystem.config;

import com.ddaying.kakaopay.keysystem.config.redis.EmbeddedRedisConfig;
import com.ddaying.kakaopay.keysystem.config.redis.RedisRepositoryConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
//@Documented
@ComponentScan(basePackages = {"com.ddaying.kakaopay.keysystem.support"}, excludeFilters = {})
@Import({EmbeddedRedisConfig.class, RedisRepositoryConfig.class})
public @interface KeySystemComponentTest {
}
