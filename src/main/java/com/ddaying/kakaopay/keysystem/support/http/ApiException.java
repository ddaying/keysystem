package com.ddaying.kakaopay.keysystem.support.http;

import lombok.Getter;

public class ApiException extends RuntimeException {

    @Getter
    private ApiStatusResponsible apiStatus;

    public ApiException(ApiStatusResponsible apiStatus) {
        super(apiStatus.getMessage());
        this.apiStatus = apiStatus;
    }

}
