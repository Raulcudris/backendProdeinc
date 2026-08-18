package com.system.crosscutting.exceptions;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WorkControlExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            final IllegalArgumentException exception
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_ERROR",
                exception.getMessage()
        );
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Map<String, Object>> handleDateTimeParse(
            final DateTimeParseException exception
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "DATE_FORMAT_ERROR",
                "Formato de fecha inválido. Formato esperado: dd-MM-yyyy."
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidJson(
            final HttpMessageNotReadableException exception
    ) {
        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "INVALID_JSON",
                "El cuerpo de la solicitud no tiene un formato JSON válido o contiene datos incompatibles."
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrity(
            final DataIntegrityViolationException exception
    ) {
        String message =
                "Error de integridad de datos. Revisa longitudes, campos obligatorios o llaves duplicadas.";

        if (exception.getMostSpecificCause() != null
                && exception.getMostSpecificCause().getMessage() != null) {

            String cause = exception.getMostSpecificCause()
                    .getMessage()
                    .toLowerCase();

            if (cause.contains("data too long")) {
                message =
                        "Uno de los campos enviados supera la longitud permitida por la base de datos.";
            }

            if (cause.contains("duplicate")) {
                message =
                        "Ya existe un registro con una llave única enviada.";
            }

            if (cause.contains("cannot be null")) {
                message =
                        "Uno de los campos obligatorios llegó vacío o nulo.";
            }

            if (cause.contains("foreign key")) {
                message =
                        "No se puede completar la operación porque existe una restricción de llave foránea.";
            }
        }

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                "DATA_INTEGRITY_ERROR",
                message
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(
            final Exception exception
    ) {
        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                "Error interno procesando la solicitud."
        );
    }

    private ResponseEntity<Map<String, Object>> buildResponse(
            final HttpStatus status,
            final String code,
            final String message
    ) {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("code", code);
        response.put("message", message);

        return ResponseEntity
                .status(status)
                .body(response);
    }
}