package com.ddaying.kakaopay.keysystem.support.http;

public interface ApiStatusResponsible {
    Integer getCode();
    String getMessage();

    default boolean isFailed() {
        return false;
    }

    default boolean isSuccess() {
        return true;
    }
}
