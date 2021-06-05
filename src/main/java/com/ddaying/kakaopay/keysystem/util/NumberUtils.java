package com.ddaying.kakaopay.keysystem.util;

public class NumberUtils {


    /**
     * N 자릿수 초기 숫자 생성
     * @param length ex. 10
     * @return ex. 1000000000
     */
    public static Long generator(int length) {
        StringBuilder sb = new StringBuilder("1");
        while (length > 1) {
            sb.append("0");
            length--;
        }
        return Long.parseLong(sb.toString());
    }
}
