package com.ddaying.kakaopay.keysystem.util;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UtilsTest {

    @Test
    public void n자리_숫자_생성_테스트() {
        assertThat(NumberUtils.generator(3)).isEqualTo(100);
        assertThat(NumberUtils.generator(7)).isEqualTo(1_000_000);
        assertThat(NumberUtils.generator(10)).isEqualTo(1_000_000_000);
    }
}
