package com.ddaying.kakaopay.keysystem.domain.keychannel;

import com.ddaying.kakaopay.keysystem.config.KeySystemComponentTest;
import com.ddaying.kakaopay.keysystem.config.RepositoryTestConfig;
import com.ddaying.kakaopay.keysystem.domain.SystemType;
import com.ddaying.kakaopay.keysystem.support.http.ApiException;
import com.ddaying.kakaopay.keysystem.support.http.ApiStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("memory")
@KeySystemComponentTest
@ContextConfiguration(classes = {RepositoryTestConfig.class})
@Transactional
@SpringBootTest
@Sql(scripts = {"classpath:init-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class KeyChannelServiceTest {

    @Autowired
    private KeyChannelService keyChannelService;


    @Test
    public void 생성_테스트() {
        KeyChannel keyChannel = keyChannelService.create("policy-number", "보험 증서 번호에 사용할 KEY 값으로 테이블 PK로 사용", SystemType.NUMBER, "mysql", 10);
        assertThat(keyChannel.getId()).isNotNull();
    }

    @Test
    public void 중복_생성시_예외_테스트() {
        ApiException apiException = Assert.assertThrows(ApiException.class, () -> keyChannelService.create("claim-number", "고객센터에서 고개 문의 사항이 접수될 때 사용하는 KEY", SystemType.STRING));
        assertThat(apiException.getMessage()).isEqualTo(ApiStatus.ALREADY_REGISTERED_KEY.getMessage());
    }

}
