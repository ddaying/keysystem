package com.ddaying.kakaopay.keysystem.domain.system;


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
@ContextConfiguration(classes = {RepositoryTestConfig.class})
@Transactional
@SpringBootTest
@Sql(scripts = {"classpath:init-data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class SystemRepositoryTest {

    @Autowired
    private SystemRepository systemRepository;


    @Test
    public void create() {
        System system = new System();
        system.setName("policy-number");
        system.setDescription("보험 증서 번호에 사용할 KEY 값으로 테이블 PK로 사용 - TEST" + (Math.random() * 10000) );
        system.setType(SystemType.NUMBER);
        system.setGenerator("mysql");
        system.setLength(10);

        System newSystem = systemRepository.save(system);
        assertThat(newSystem.getId()).isNotNull();
        assertThat(newSystem.getName()).isEqualTo(system.getName());
        assertThat(newSystem.getDescription()).isEqualTo(system.getDescription());
        assertThat(newSystem.getType()).isEqualTo(system.getType());
        assertThat(newSystem.getGenerator()).isEqualTo(system.getGenerator());
        assertThat(newSystem.getLength()).isEqualTo(system.getLength());
    }

    @Test
    public void select() {
        Optional<System> optionalSystem = systemRepository.findByName("claim-number");
        assertThat(optionalSystem.isPresent()).isEqualTo(true);

        System system = optionalSystem.get();
        assertThat(system.getId()).isEqualTo(1L);
        assertThat(system.getDescription()).isEqualTo("고객센터에서 고객 문의사항이 접수될 때 사용하는 Key");
        assertThat(system.getType()).isEqualTo(SystemType.STRING);
    }
}
