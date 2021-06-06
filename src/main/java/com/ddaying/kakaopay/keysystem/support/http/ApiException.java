package com.ddaying.kakaopay.keysystem.support.http;

import lombok.Getter;

public class ApiException extends RuntimeException {

    @Getter
    private ApiStatusResponsible apiStatus;

    public ApiException(ApiStatusResponsible apiStatus) {
        super(apiStatus.getMessage());
        this.apiStatus = apiStatus;
    }

    public ApiException(ApiStatusResponsible apiStatus, String message) {
        super(message);
        this.apiStatus = apiStatus;
        // TODO 커스텀 메시지 출력은 필요시 정의
    }

}
