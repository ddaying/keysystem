package com.ddaying.kakaopay.keysystem.domain.keydata;

import com.ddaying.kakaopay.keysystem.config.KeySystemComponentTest;
import com.ddaying.kakaopay.keysystem.config.RepositoryTestConfig;
import com.ddaying.kakaopay.keysystem.domain.keychannel.KeyChannel;
import com.ddaying.kakaopay.keysystem.domain.keychannel.KeyChannelRepository;
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
public class KeyDataServiceTest {

    @Autowired
    private KeyDataService keyDataService;

    @Autowired
    private KeyChannelRepository keyChannelRepository;

    @Test
    public void 키_저장_테스트() {
        KeyChannel keyChannel = keyChannelRepository.getOne(1L);
        assertThat(keyChannel.getKeyData()).isEmpty();

        String value = "ABCDEFG";
        KeyData keyData = keyDataService.create(keyChannel, value);
        keyChannel.addKey(keyData);

        assertThat(keyData.getValue()).isEqualTo(value);
        assertThat(keyChannel.getKeyData().size()).isEqualTo(1);
        assertThat(keyChannel.getKeyData().get(0).getValue()).isEqualTo(value);
    }

    @Test
    public void 중복_키_생성() {
        KeyChannel keyChannel = keyChannelRepository.getOne(1L);
        assertThat(keyChannel.getKeyData()).isEmpty();

        String value = "XXXX-XXXX-XXXX-XXXX";
        KeyData keyData1 = keyDataService.create(keyChannel, value);
        keyChannel.addKey(keyData1);

        ApiException apiException = Assert.assertThrows(ApiException.class, () -> keyDataService.create(keyChannel, value));
        assertThat(apiException.getMessage()).isEqualTo(ApiStatus.ALREADY_GENERATED_KEY.getMessage());
    }
}
