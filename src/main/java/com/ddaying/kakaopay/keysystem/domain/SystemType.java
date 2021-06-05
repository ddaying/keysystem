package com.ddaying.kakaopay.keysystem.domain;

import com.ddaying.kakaopay.keysystem.support.http.ApiException;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;

import java.util.Arrays;

public enum SystemType {

    NUMBER, STRING;

    public boolean isNumber() {
        return this.equals(NUMBER);
    }

    public static SystemType from(String value) {
        return Arrays.stream(values())
                .filter(v -> v.name().equals(value.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new ApiException(ApiStatus.INVALID_SYSTEM_TYPE));
    }
}
