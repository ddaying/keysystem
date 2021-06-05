package com.ddaying.kakaopay.keysystem.domain.key;

import com.ddaying.kakaopay.keysystem.domain.key.view.KeyRegisterRequest;
import com.ddaying.kakaopay.keysystem.support.http.ApiResult;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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

    // 각 Key 별로 새로운 키 발급
    @GetMapping("/{key}")
    public ApiResult generator(@PathVariable String key) {

        return ApiResult.of(ApiStatus.SUCCESS, keyFacade.generator(key));
    }

}
