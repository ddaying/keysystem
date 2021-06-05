package com.ddaying.kakaopay.keysystem.util;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class NumberUtils {


    /**
     * N 자릿수 초기 숫자 생성
     *
     * @param length ex. 10
     * @return ex. 1000000000
     */
    public static Long generator(int length) {
        AtomicLong number = new AtomicLong(1);

        IntStream.range(0, length-1).forEach(n -> {
            number.updateAndGet(v -> v * 10);
        });

        return number.get();
    }
}
