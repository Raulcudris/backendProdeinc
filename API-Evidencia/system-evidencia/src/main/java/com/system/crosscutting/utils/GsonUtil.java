package com.system.crosscutting.utils;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

public final class GsonUtil {

    private static final DateTimeFormatter LOCAL_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter LOCAL_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private GsonUtil() {
    }

    public static Gson getGson() {
        return createGson(false);
    }

    public static Gson getGson(boolean prettyPrinting) {
        return createGson(prettyPrinting);
    }

    private static Gson createGson(boolean prettyPrinting) {
        GsonBuilder builder = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, localDateSerializer())
                .registerTypeAdapter(LocalDate.class, localDateDeserializer())
                .registerTypeAdapter(LocalDateTime.class, localDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer());

        if (prettyPrinting) {
            builder.setPrettyPrinting();
        }

        return builder.create();
    }

    private static JsonSerializer<LocalDate> localDateSerializer() {
        return (LocalDate src, Type typeOfSrc, com.google.gson.JsonSerializationContext context) -> {
            if (src == null) {
                return null;
            }

            return new JsonPrimitive(src.format(LOCAL_DATE_FORMATTER));
        };
    }

    private static JsonDeserializer<LocalDate> localDateDeserializer() {
        return (json, typeOfT, context) -> {
            if (json == null || json.isJsonNull()) {
                return null;
            }

            return LocalDate.parse(json.getAsString(), LOCAL_DATE_FORMATTER);
        };
    }

    private static JsonSerializer<LocalDateTime> localDateTimeSerializer() {
        return (LocalDateTime src, Type typeOfSrc, com.google.gson.JsonSerializationContext context) -> {
            if (src == null) {
                return null;
            }

            return new JsonPrimitive(src.format(LOCAL_DATE_TIME_FORMATTER));
        };
    }

    private static JsonDeserializer<LocalDateTime> localDateTimeDeserializer() {
        return (json, typeOfT, context) -> {
            if (json == null || json.isJsonNull()) {
                return null;
            }

            String value = json.getAsString();

            if (value.length() == 10) {
                return LocalDate.parse(value, LOCAL_DATE_FORMATTER).atStartOfDay();
            }

            return LocalDateTime.parse(value, LOCAL_DATE_TIME_FORMATTER);
        };
    }
}