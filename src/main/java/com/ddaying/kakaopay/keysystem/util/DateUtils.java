package com.ddaying.kakaopay.keysystem.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    // 기본 포맷
    private static final String yyyyMMdd = "yyyy-MM-dd";
    private static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    public static String yyyyMMdd(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(yyyyMMdd));
    }

    public static LocalDate asLocalDateYyyyMmDd(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(yyyyMMdd));
    }

    public static String toYyyyMMddHHmmss(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(yyyyMMddHHmmss));
    }

    public static LocalDateTime asYyyyMMddHHmmss(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(yyyyMMddHHmmss));
    }

}
