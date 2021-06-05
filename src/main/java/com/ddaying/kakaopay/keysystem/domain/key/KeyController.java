package com.ddaying.kakaopay.keysystem.domain.key;

import com.ddaying.kakaopay.keysystem.domain.key.view.KeyRegisterRequest;
import com.ddaying.kakaopay.keysystem.support.http.ApiResult;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/key")
@RestController
public class KeyController {

    @Autowired
    private KeyFacade keyFacade;


    // KEY 시스템에 정보 등록
    @PostMapping("/register")
    public ApiResult register(@RequestBody KeyRegisterRequest request) {

        keyFacade.register(request);

        return ApiResult.of(ApiStatus.SUCCESS);
    }

}
