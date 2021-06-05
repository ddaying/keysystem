package com.ddaying.kakaopay.keysystem.domain.keydata;

import com.ddaying.kakaopay.keysystem.config.KeySystemComponentTest;
import com.ddaying.kakaopay.keysystem.config.RepositoryTestConfig;
import com.ddaying.kakaopay.keysystem.domain.SystemType;
import com.ddaying.kakaopay.keysystem.domain.keychannel.KeyChannel;
import com.ddaying.kakaopay.keysystem.domain.keydata.view.KeyChannelRegisterRequest;
import com.ddaying.kakaopay.keysystem.domain.keydata.view.KeyDataView;
import com.ddaying.kakaopay.keysystem.domain.keychannel.KeyChannelRepository;
import com.ddaying.kakaopay.keysystem.support.http.ApiException;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;
import com.ddaying.kakaopay.keysystem.support.redis.RedisService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("memory")
@KeySystemComponentTest
@ContextConfiguration(classes = {RepositoryTestConfig.class})
@Transactional
@SpringBootTest
@Sql(scripts = {"classpath:init-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class KeyDataFacadeTest {

    @Autowired
    private KeyDataFacade keyDataFacade;

    @Autowired
    private KeyChannelRepository keyChannelRepository;

    @Autowired
    private RedisService redisService;


    @Test
    public void 키_시스템_등록() {
        KeyChannelRegisterRequest request = KeyChannelRegisterRequest.builder()
                .key("policy-number")
                .description("보험 증서 번호에 사용할 KEY 값으로 테이블 PK로 사용")
                .type("number")
                .generator("mysql")
                .minLength(10)
                .build();

        keyDataFacade.register(request);

        Optional<KeyChannel> optionalSystem = keyChannelRepository.findByName(request.getKey());
        assertThat(optionalSystem.isPresent()).isEqualTo(true);

        KeyChannel keyChannel = optionalSystem.get();
        assertThat(keyChannel.getDescription()).isEqualTo(request.getDescription());
        assertThat(keyChannel.getType()).isEqualTo(SystemType.NUMBER);
        assertThat(keyChannel.getGenerator()).isEqualTo(request.getGenerator());
        assertThat(keyChannel.getLength()).isEqualTo(request.getMinLength());
    }

    @Test
    public void 키_시스템_등록_필수_파라미터_누락_체크() {
        KeyChannelRegisterRequest request = KeyChannelRegisterRequest.builder()
                .key("policy-number")
                .description("보험 증서 번호에 사용할 KEY 값으로 테이블 PK로 사용")
                .type("number")
                .build();

        ApiException apiException = Assert.assertThrows(ApiException.class, () -> keyDataFacade.register(request));
        assertThat(apiException.getMessage()).isEqualTo(ApiStatus.INVALID_REQUIRE_PARAMETER.getMessage());
    }

    @Test
    public void 숫자형_키_발급() {
        // 초기화
        redisService.delete("custom-key");

        // 발급
        KeyDataView keyDataView = keyDataFacade.generator("custom-key");
        assertThat(keyDataView.getValue()).isEqualTo("1000000000");
    }

}
