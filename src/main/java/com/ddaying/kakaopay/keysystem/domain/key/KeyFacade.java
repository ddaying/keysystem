package com.ddaying.kakaopay.keysystem.domain.key;

import com.ddaying.kakaopay.keysystem.domain.SystemType;
import com.ddaying.kakaopay.keysystem.domain.key.view.KeyRegisterRequest;
import com.ddaying.kakaopay.keysystem.domain.key.view.KeyView;
import com.ddaying.kakaopay.keysystem.domain.system.System;
import com.ddaying.kakaopay.keysystem.domain.system.SystemService;
import com.ddaying.kakaopay.keysystem.support.http.ApiException;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;
import com.ddaying.kakaopay.keysystem.support.redis.RedisService;
import com.ddaying.kakaopay.keysystem.util.NumberUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Transactional
@Component
public class KeyFacade {

    @Autowired
    private SystemService systemService;

    @Autowired
    private KeyService keyService;

    @Autowired
    public RedisService redisService;


    public void register(KeyRegisterRequest request) {

        SystemType type = SystemType.from(request.getType());
        this.validate(type, request);

        systemService.create(request.getKey(), request.getDescription(), type, request.getGenerator(), request.getMinLength());
    }

    private void validate(SystemType type, KeyRegisterRequest request) {
        if (type.isNumber()) {
            if (Strings.isNullOrEmpty(request.getGenerator()) || Objects.isNull(request.getMinLength())) {
                throw new ApiException(ApiStatus.INVALID_REQUIRE_PARAMETER);
            }
        }
    }

    public KeyView generator(String keyName) {
        String value = "";

        // 1) 키 등록 여부 확인
        System system = systemService.getByName(keyName);
        if (system.isDeleted()) {
            throw new ApiException(ApiStatus.DELETED_KEY);
        }

        // 2) 키 타입에 따라 고유 키 생성
        if (system.getType().isNumber()) {
            value = this.createNumber(system);
        } else {
            // TODO String 정의 필요
        }

        Key key = keyService.create(system, value);
        system.addKey(key);

        return KeyView.builder()
                .value(value)
                .build();
    }

    // 시스템별 고유 키 생성
    private String createNumber(System system) {
        String value;

        if (system.getGenerator().equals("redis")) {
            Long _value = redisService.get(system.getName(), Long.class);
            if (Objects.isNull(_value)) {
                _value = NumberUtils.generator(system.getLength());
                redisService.set(system.getName(), _value);
            } else if (String.valueOf(_value).length() < system.getLength()) {
                log.info("기존에 생성된 value 값이 정의된 min-length 정책에 어긋나 새로 생성함");
                _value = NumberUtils.generator(system.getLength());
            } else {
                _value = redisService.increment(system.getName());
            }
            value = String.valueOf(_value);
        } else {
            // TODO 추후 generator 별 정의 및 추상화 필요
            throw new ApiException(ApiStatus.INVALID_SYSTEM_GENERATOR);
        }

        return value;
    }

}
