package com.ddaying.kakaopay.keysystem.domain.keychannel;


import com.ddaying.kakaopay.keysystem.config.KeySystemComponentTest;
import com.ddaying.kakaopay.keysystem.config.RepositoryTestConfig;
import com.ddaying.kakaopay.keysystem.domain.SystemType;
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
public class KeyChannelRepositoryTest {

    @Autowired
    private KeyChannelRepository keyChannelRepository;


    @Test
    public void create() {
        KeyChannel keyChannel = new KeyChannel();
        keyChannel.setName("policy-number");
        keyChannel.setDescription("보험 증서 번호에 사용할 KEY 값으로 테이블 PK로 사용 - TEST" + (Math.random() * 10000) );
        keyChannel.setType(SystemType.NUMBER);
        keyChannel.setGenerator("mysql");
        keyChannel.setLength(10);

        KeyChannel newKeyChannel = keyChannelRepository.save(keyChannel);
        assertThat(newKeyChannel.getId()).isNotNull();
        assertThat(newKeyChannel.getName()).isEqualTo(keyChannel.getName());
        assertThat(newKeyChannel.getDescription()).isEqualTo(keyChannel.getDescription());
        assertThat(newKeyChannel.getType()).isEqualTo(keyChannel.getType());
        assertThat(newKeyChannel.getGenerator()).isEqualTo(keyChannel.getGenerator());
        assertThat(newKeyChannel.getLength()).isEqualTo(keyChannel.getLength());
    }

    @Test
    public void select() {
        Optional<KeyChannel> optionalSystem = keyChannelRepository.findByName("claim-number");
        assertThat(optionalSystem.isPresent()).isEqualTo(true);

        KeyChannel keyChannel = optionalSystem.get();
        assertThat(keyChannel.getId()).isEqualTo(1L);
        assertThat(keyChannel.getDescription()).isEqualTo("고객센터에서 고객 문의사항이 접수될 때 사용하는 Key");
        assertThat(keyChannel.getType()).isEqualTo(SystemType.STRING);
    }
}
