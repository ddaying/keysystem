package com.ddaying.kakaopay.keysystem.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class JsonHelper {

    private static final Gson GSON = new GsonBuilder()
            .disableInnerClassSerialization()
            .disableHtmlEscaping()
            .create();

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static String toJson(Object obj, Type type) {
        return GSON.toJson(obj, type);
    }

    public static <T> T fromJson(String json, Class<T> clz) {
        return GSON.fromJson(json, clz);
    }

    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }

}
