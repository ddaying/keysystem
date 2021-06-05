package com.ddaying.kakaopay.keysystem.domain.keydata;

import com.ddaying.kakaopay.keysystem.domain.SystemType;
import com.ddaying.kakaopay.keysystem.domain.keychannel.KeyChannel;
import com.ddaying.kakaopay.keysystem.domain.keychannel.KeyChannelService;
import com.ddaying.kakaopay.keysystem.domain.keydata.view.KeyChannelRegisterRequest;
import com.ddaying.kakaopay.keysystem.domain.keydata.view.KeyDataView;
import com.ddaying.kakaopay.keysystem.support.http.ApiException;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;
import com.ddaying.kakaopay.keysystem.support.redis.RedisService;
import com.ddaying.kakaopay.keysystem.util.KeyUtils;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Transactional
@Component
public class KeyDataFacade {

    @Autowired
    private KeyChannelService keyChannelService;

    @Autowired
    private KeyDataService keyDataService;

    @Autowired
    public RedisService redisService;


    public void register(KeyChannelRegisterRequest request) {

        SystemType type = SystemType.from(request.getType());
        this.validate(type, request);

        keyChannelService.create(request.getKey(), request.getDescription(), type, request.getGenerator(), request.getMinLength());
    }

    private void validate(SystemType type, KeyChannelRegisterRequest request) {
        if (type.isNumber()) {
            if (Strings.isNullOrEmpty(request.getGenerator()) || Objects.isNull(request.getMinLength())) {
                throw new ApiException(ApiStatus.INVALID_REQUIRE_PARAMETER);
            }
        }
    }

    public KeyDataView generator(String keyName) {
        String value = "";

        // 1) 키 등록 여부 확인
        KeyChannel keyChannel = keyChannelService.getByName(keyName);
        if (keyChannel.isDeleted()) {
            throw new ApiException(ApiStatus.DELETED_KEY);
        }

        // 2) 키 타입에 따라 고유 키 생성
        if (keyChannel.getType().isNumber()) {
            value = this.createNumber(keyChannel);

            // 최근 생성된 키 업데이트
            keyChannel.setValue(Long.parseLong(value));
        } else {
            value = KeyUtils.generator();
        }

        KeyData keyData = keyDataService.create(keyChannel, value);
        keyChannel.addKey(keyData);

        return KeyDataView.builder()
                .value(value)
                .build();
    }

    // 시스템별 고유 키 생성
    private String createNumber(KeyChannel keyChannel) {
        String value;

        if (keyChannel.getGenerator().equals("redis")) {
            Long _value = redisService.get(keyChannel.getName(), Long.class);
            if (Objects.isNull(_value)) {
                _value = KeyUtils.generator(keyChannel.getLength());
                redisService.set(keyChannel.getName(), _value);
            } else if (String.valueOf(_value).length() < keyChannel.getLength()) {
                log.info("기존에 생성된 value 값이 정의된 min-length 정책에 어긋나 새로 생성함");
                _value = KeyUtils.generator(keyChannel.getLength());
            } else {
                _value = redisService.increment(keyChannel.getName());
            }
            value = String.valueOf(_value);
        } else {
            // TODO 추후 generator 별 정의 및 추상화 필요
            throw new ApiException(ApiStatus.INVALID_SYSTEM_GENERATOR);
        }

        return value;
    }

}
