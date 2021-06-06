package com.ddaying.kakaopay.keysystem.interceptor;

import com.ddaying.kakaopay.keysystem.util.DateUtils;
import com.ddaying.kakaopay.keysystem.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class MeasureInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("sTime", startTime);

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        long startTime = (long)request.getAttribute("sTime");
        long processedTime = System.currentTimeMillis() - startTime;

        log.info("[{}] {} {} {} {}ms ", DateUtils.now(), WebUtil.getRemoteIp(request), request.getRequestURI(), response.getStatus(), processedTime);

        super.afterCompletion(request, response, handler, ex);
    }

}
