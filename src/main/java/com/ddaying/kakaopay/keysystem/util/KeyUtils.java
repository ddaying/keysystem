package com.ddaying.kakaopay.keysystem.util;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class KeyUtils {


    /**
     * N 자릿수 초기 숫자 생성
     *
     * @param length ex. 10
     * @return ex. 1000000000
     */
    public static Long generator(int length) {
        AtomicLong number = new AtomicLong(1);

        IntStream.range(0, length - 1).forEach(n -> {
            number.updateAndGet(v -> v * 10);
        });

        return number.get();
    }

    /**
     * 문자열 생성
     * @return ex. FMTJ-WGE7-UHYV-Z0KF
     */
    public static String generator() {

        // 아스키코드로 0~Z 까지의 문자열을 랜돔으로 생성
        int leftLimit = 48; // '0'
        int rightLimit = 90; // 'Z'
        Random random = new Random();

        String uuid = random.ints(leftLimit,rightLimit + 1)
                .filter(i -> (i <= 57) || (i >= 65 && i <= 90))
                .limit(16)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return String.format("%s-%s-%s-%s", uuid.substring(0, 4), uuid.substring(4, 8), uuid.substring(8, 12), uuid.substring(12, 16));
    }
}
