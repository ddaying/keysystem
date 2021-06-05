package com.ddaying.kakaopay.keysystem.domain.key;

import com.ddaying.kakaopay.keysystem.config.RepositoryTestConfig;
import com.ddaying.kakaopay.keysystem.domain.SystemType;
import com.ddaying.kakaopay.keysystem.domain.key.view.KeyRegisterRequest;
import com.ddaying.kakaopay.keysystem.domain.system.System;
import com.ddaying.kakaopay.keysystem.domain.system.SystemRepository;
import com.ddaying.kakaopay.keysystem.support.http.ApiException;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("memory")
@ContextConfiguration(classes = {RepositoryTestConfig.class})
@Transactional
@SpringBootTest
public class KeyFacadeTest {

    @Autowired
    private KeyFacade keyFacade;

    @Autowired
    private SystemRepository systemRepository;


    @Test
    public void 생성_테스트() {
        KeyRegisterRequest request = KeyRegisterRequest.builder()
                .key("policy-number")
                .description("보험 증서 번호에 사용할 KEY 값으로 테이블 PK로 사용")
                .type("number")
                .generator("mysql")
                .minLength(10)
                .build();

        keyFacade.register(request);

        Optional<System> optionalSystem = systemRepository.findByName(request.getKey());
        assertThat(optionalSystem.isPresent()).isEqualTo(true);

        System system = optionalSystem.get();
        assertThat(system.getDescription()).isEqualTo(request.getDescription());
        assertThat(system.getType()).isEqualTo(SystemType.NUMBER);
        assertThat(system.getGenerator()).isEqualTo(request.getGenerator());
        assertThat(system.getLength()).isEqualTo(request.getMinLength());
    }

    @Test
    public void 필수_파라미터_체크() {
        KeyRegisterRequest request = KeyRegisterRequest.builder()
                .key("policy-number")
                .description("보험 증서 번호에 사용할 KEY 값으로 테이블 PK로 사용")
                .type("number")
                .build();

        ApiException apiException = Assert.assertThrows(ApiException.class, () -> keyFacade.register(request));
        assertThat(apiException.getMessage()).isEqualTo(ApiStatus.INVALID_REQUIRE_PARAMETER.getMessage());
    }

}
