package com.ddaying.kakaopay.keysystem.util;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.Optional;

public class CustomLocalDateSerializer implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate>  {
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(DateUtils.yyyyMMdd(src));
    }

    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        return Optional.ofNullable(json.getAsString())
                .map((DateUtils::asLocalDateYyyyMmDd))
                .orElse(null);
    }
}
