package com.ddaying.kakaopay.keysystem.domain.key;

import com.ddaying.kakaopay.keysystem.domain.SystemType;
import com.ddaying.kakaopay.keysystem.domain.key.view.KeyRegisterRequest;
import com.ddaying.kakaopay.keysystem.domain.system.SystemService;
import com.ddaying.kakaopay.keysystem.support.http.ApiException;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Transactional
@Component
public class KeyFacade {

    @Autowired
    private SystemService systemService;


    public void register(KeyRegisterRequest request) {

        SystemType type = SystemType.from(request.getType());
        this.validate(type, request);

        systemService.create(request.getKey(), request.getDescription(), type, request.getGenerator(), request.getMinLength());
    }

    private void validate(SystemType type, KeyRegisterRequest request) {
        if (type.isNumber()) {
            if (Strings.isNullOrEmpty(request.getGenerator()) || Objects.isNull(request.getMinLength())) {
                throw new ApiException(ApiStatus.INVALID_REQUIRE_PARAMETER);
            }
        }
    }
}
