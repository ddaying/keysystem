package com.ddaying.kakaopay.keysystem.domain.key;

import com.ddaying.kakaopay.keysystem.config.KeySystemComponentTest;
import com.ddaying.kakaopay.keysystem.config.RepositoryTestConfig;
import com.ddaying.kakaopay.keysystem.domain.system.System;
import com.ddaying.kakaopay.keysystem.domain.system.SystemRepository;
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
public class KeyServiceTest {

    @Autowired
    private KeyService keyService;

    @Autowired
    private SystemRepository systemRepository;

    @Test
    public void 키_저장_테스트() {
        System system = systemRepository.getOne(1L);
        assertThat(system.getKeys()).isEmpty();

        String value = "ABCDEFG";
        Key key = keyService.create(system, value);
        system.addKey(key);

        assertThat(key.getValue()).isEqualTo(value);
        assertThat(system.getKeys().size()).isEqualTo(1);
        assertThat(system.getKeys().get(0).getValue()).isEqualTo(value);
    }
}
