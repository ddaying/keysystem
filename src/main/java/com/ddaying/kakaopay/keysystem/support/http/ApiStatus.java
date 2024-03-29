package com.ddaying.kakaopay.keysystem.support.http;

import lombok.Getter;

@Getter
public enum ApiStatus implements ApiStatusResponsible {

    INTERNAL_ERROR(-500, "서버 에러"),
    INVALID_REQUIRE_PARAMETER(-400, "필수 값이 존재하지 않습니다."),

    SUCCESS(0, "성공"),

    INVALID_SYSTEM_TYPE(-1000, "정의 되지 않은 타입입니다."),
    ALREADY_REGISTERED_KEY(-1001, "이미 등록된 키 입니다."),
    INVALID_KEY(-1002, "등록되지 않은 키 입니다."),
    DELETED_KEY(-1003, "삭제된 키 입니다."),
    INVALID_SYSTEM_GENERATOR(-1004, "정의 되지 않은 generator 입니다."),

    ALREADY_GENERATED_KEY(-2000, "중복 키가 발생하였습니다. 다시 시도해 주세요."),
    ;

    private Integer code;
    private String message;

    ApiStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
