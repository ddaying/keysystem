package com.ddaying.kakaopay.keysystem.domain.key.view;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;

@Builder // 테스트 위해 builder 생성
@Getter
public class KeyRegisterRequest {

    // 키 명칭
    private String key;
    // 키 설명
    private String description;
    // 키 타입
    private String type;

    // 생성 타입
    private String generator;
    // 키 타입이 숫자 형 일 경우, 최소 자릿수
    @SerializedName("min-length")
    private Integer minLength;
}
