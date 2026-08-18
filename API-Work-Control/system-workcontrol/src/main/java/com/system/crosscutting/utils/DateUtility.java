package com.system.crosscutting.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.experimental.UtilityClass;

/**
 * Utilidad para manejo básico de fechas modernas de Java.
 *
 * Esta clase permite centralizar conversiones simples entre texto, LocalDate
 * y LocalDateTime usando formatos ISO.
 */
@UtilityClass
public class DateUtility {

    /**
     * Formato ISO para fechas:
     * 2026-06-02
     */
    public static final DateTimeFormatter ISO_LOCAL_DATE = DateTimeFormatter.ISO_LOCAL_DATE;

    /**
     * Formato ISO para fecha y hora:
     * 2026-06-02T09:30:00
     */
    public static final DateTimeFormatter ISO_LOCAL_DATE_TIME = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    /**
     * Convierte texto ISO a LocalDate.
     *
     * @param value texto con formato yyyy-MM-dd.
     * @return LocalDate convertido o null si el valor viene vacío.
     */
    public static LocalDate toLocalDate(final String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return LocalDate.parse(value, ISO_LOCAL_DATE);
    }

    /**
     * Convierte texto ISO a LocalDateTime.
     *
     * @param value texto con formato yyyy-MM-ddTHH:mm:ss.
     * @return LocalDateTime convertido o null si el valor viene vacío.
     */
    public static LocalDateTime toLocalDateTime(final String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return LocalDateTime.parse(value, ISO_LOCAL_DATE_TIME);
    }

    /**
     * Convierte LocalDate a texto ISO.
     *
     * @param value fecha LocalDate.
     * @return texto en formato yyyy-MM-dd o null si la fecha es null.
     */
    public static String toString(final LocalDate value) {
        if (value == null) {
            return null;
        }

        return value.format(ISO_LOCAL_DATE);
    }

    /**
     * Convierte LocalDateTime a texto ISO.
     *
     * @param value fecha y hora LocalDateTime.
     * @return texto en formato yyyy-MM-ddTHH:mm:ss o null si la fecha es null.
     */
    public static String toString(final LocalDateTime value) {
        if (value == null) {
            return null;
        }

        return value.format(ISO_LOCAL_DATE_TIME);
    }
}