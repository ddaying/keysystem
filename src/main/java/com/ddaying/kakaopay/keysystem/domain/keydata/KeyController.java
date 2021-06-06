package com.ddaying.kakaopay.keysystem.domain.keydata;

import com.ddaying.kakaopay.keysystem.domain.keydata.view.KeyChannelRegisterRequest;
import com.ddaying.kakaopay.keysystem.support.http.ApiResult;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api/key")
@RestController
public class KeyController {

    @Autowired
    private KeyDataFacade keyDataFacade;


    // KEY 시스템에 정보 등록
    @PostMapping("/register")
    public ApiResult register(@RequestBody KeyChannelRegisterRequest request) {

        keyDataFacade.register(request);

        return ApiResult.of(ApiStatus.SUCCESS);
    }

    // 각 Key 별로 새로운 키 발급
    @GetMapping("/{key}")
    public ApiResult generator(@PathVariable String key) {

        return ApiResult.of(ApiStatus.SUCCESS, keyDataFacade.generator(key));
    }

}
