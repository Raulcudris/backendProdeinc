package com.system.crosscutting.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.system.crosscutting.domain.adapter.HibernateProxyTypeAdapter;

import lombok.experimental.UtilityClass;

/**
 * Utilidad centralizada para construir instancias de Gson.
 *
 * Incluye soporte para:
 * - HibernateProxyTypeAdapter.
 * - Exclusión de campos o clases marcadas con JsonIgnore.
 * - Serialización de nulos.
 * - Fechas tipo java.util.Date mediante setDateFormat.
 * - Fechas modernas de Java: LocalDate y LocalDateTime.
 *
 * El soporte para LocalDate y LocalDateTime es necesario en Java 17,
 * porque Gson no debe intentar acceder por reflexión a campos internos
 * del paquete java.time.
 */
@UtilityClass
public class GsonUtil {

    /**
     * Obtiene una instancia de Gson usando el formato por defecto y aplicando exclusiones.
     *
     * @return instancia de Gson configurada.
     */
    public static Gson getGson() {
        return getGson("MMM dd, yyyy HH:mm:ss", true);
    }

    /**
     * Obtiene una instancia de Gson usando el formato por defecto.
     *
     * @param exclude indica si se deben excluir campos o clases con JsonIgnore.
     * @return instancia de Gson configurada.
     */
    public static Gson getGson(boolean exclude) {
        return getGson("MMM dd, yyyy HH:mm:ss", exclude);
    }

    /**
     * Obtiene una instancia de Gson personalizada.
     *
     * @param format formato para tipos Date tradicionales.
     * @param exclude indica si se deben excluir campos o clases con JsonIgnore.
     * @return instancia de Gson configurada.
     */
    public static Gson getGson(String format, boolean exclude) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder
                .registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY)
                .registerTypeAdapter(LocalDate.class, localDateSerializer())
                .registerTypeAdapter(LocalDate.class, localDateDeserializer())
                .registerTypeAdapter(LocalDateTime.class, localDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer())
                .setDateFormat(format)
                .serializeNulls();

        if (exclude) {
            gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
                @Override
                public boolean shouldSkipClass(Class<?> clazz) {
                    return clazz.getAnnotation(JsonIgnore.class) != null;
                }

                @Override
                public boolean shouldSkipField(FieldAttributes f) {
                    return f.getAnnotation(JsonIgnore.class) != null;
                }
            });
        }

        return gsonBuilder.create();
    }

    /**
     * Serializador para LocalDate.
     *
     * Convierte LocalDate a texto ISO:
     * 2026-06-02
     *
     * @return serializador de LocalDate.
     */
    private static JsonSerializer<LocalDate> localDateSerializer() {
        return (src, typeOfSrc, context) -> src == null ? null : context.serialize(src.toString());
    }

    /**
     * Deserializador para LocalDate.
     *
     * Convierte texto ISO a LocalDate:
     * 2026-06-02
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
     * Convierte LocalDateTime a texto ISO:
     * 2026-06-02T09:30:00
     *
     * @return serializador de LocalDateTime.
     */
    private static JsonSerializer<LocalDateTime> localDateTimeSerializer() {
        return (src, typeOfSrc, context) -> src == null ? null : context.serialize(src.toString());
    }

    /**
     * Deserializador para LocalDateTime.
     *
     * Convierte texto ISO a LocalDateTime:
     * 2026-06-02T09:30:00
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