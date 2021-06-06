package com.ddaying.kakaopay.keysystem.support.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ApiResult handleException(HttpServletRequest req, Exception e) {
        if (e instanceof ApiException) {
            log.error(e.getMessage(), e);
            return ApiResult.of(((ApiException) e).getApiStatus());
        }

        log.info(e.getMessage(), e);
        return ApiResult.of(ApiStatus.INTERNAL_ERROR);
    }

}
