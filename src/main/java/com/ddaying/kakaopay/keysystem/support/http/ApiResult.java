package com.ddaying.kakaopay.keysystem.support.http;


public class ApiResult<T> {

    private Integer code;
    private String message;
    private T data;

    public static <T> ApiResult<T> of(ApiStatusResponsible apiStatus, T data) {
        return new ApiResult<>(apiStatus, data);
    }

    public static <T> ApiResult<T> of(ApiStatusResponsible apiStatus) {
        return new ApiResult<>(apiStatus, null);
    }

    private ApiResult(ApiStatusResponsible apiStatus, T data) {
        this.code = apiStatus.getCode();
        this.data = data;
        this.message = apiStatus.getMessage();
    }

}
