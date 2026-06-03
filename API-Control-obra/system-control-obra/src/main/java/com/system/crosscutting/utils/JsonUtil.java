package com.system.crosscutting.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.system.crosscutting.domain.constants.Constants;

import lombok.experimental.UtilityClass;

/**
 * Utilidad para conversiones JSON usando Gson.
 *
 * Incluye soporte para LocalDate y LocalDateTime para evitar errores de reflexión
 * con Java 17 al convertir objetos que contienen fechas modernas de Java.
 */
@UtilityClass
public final class JsonUtil {

    /**
     * Gson configurado para respetar exactamente los nombres de campos.
     */
    private static final Gson GSON_IDENTITY = buildGson(FieldNamingPolicy.IDENTITY);

    /**
     * Gson configurado para usar nombres de campos en formato lower_case_with_underscores.
     */
    @SuppressWarnings("unused")
    private static final Gson GSON_UNDERSCORES = buildGson(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    /**
     * Convierte un objeto a JSON y luego a una clase destino, respetando nombres de campos.
     *
     * @param json objeto origen.
     * @param classOfT clase destino.
     * @param <T> tipo destino.
     * @return objeto convertido.
     */
    public static <T extends Object> T fromJsonIdentity(final Object json, final Class<T> classOfT) {
        return GSON_IDENTITY.fromJson(GSON_IDENTITY.toJson(json), classOfT);
    }

    /**
     * Construye una instancia de Gson con adaptadores para fechas modernas de Java.
     *
     * @param fieldNamingPolicy política de nombres de campos.
     * @return instancia de Gson configurada.
     */
    private static Gson buildGson(final FieldNamingPolicy fieldNamingPolicy) {
        return new GsonBuilder()
                .setFieldNamingPolicy(fieldNamingPolicy)
                .setDateFormat(Constants.ISO_DATE_TIME_FORMAT)
                .registerTypeAdapter(LocalDate.class, localDateSerializer())
                .registerTypeAdapter(LocalDate.class, localDateDeserializer())
                .registerTypeAdapter(LocalDateTime.class, localDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer())
                .setPrettyPrinting()
                .create();
    }

    /**
     * Serializador para LocalDate.
     *
     * @return serializador de LocalDate.
     */
    private static JsonSerializer<LocalDate> localDateSerializer() {
        return (src, typeOfSrc, context) -> src == null ? null : context.serialize(src.toString());
    }

    /**
     * Deserializador para LocalDate.
     *
     * @return deserializador de LocalDate.
     */
    private static JsonDeserializer<LocalDate> localDateDeserializer() {
        return (json, typeOfT, context) -> {
            if (json == null || json.isJsonNull()) {
                return null;
            }

            String value = json.getAsString();

            if (value == null || value.isBlank()) {
                return null;
            }

            return LocalDate.parse(value);
        };
    }

    /**
     * Serializador para LocalDateTime.
     *
     * @return serializador de LocalDateTime.
     */
    private static JsonSerializer<LocalDateTime> localDateTimeSerializer() {
        return (src, typeOfSrc, context) -> src == null ? null : context.serialize(src.toString());
    }

    /**
     * Deserializador para LocalDateTime.
     *
     * @return deserializador de LocalDateTime.
     */
    private static JsonDeserializer<LocalDateTime> localDateTimeDeserializer() {
        return (json, typeOfT, context) -> {
            if (json == null || json.isJsonNull()) {
                return null;
            }

            String value = json.getAsString();

            if (value == null || value.isBlank()) {
                return null;
            }

            return LocalDateTime.parse(value);
        };
    }
}