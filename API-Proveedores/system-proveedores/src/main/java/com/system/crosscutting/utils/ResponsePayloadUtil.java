package com.system.crosscutting.utils;
import java.util.List;
import com.system.crosscutting.exceptions.Main.EBusinessException;

public final class ResponsePayloadUtil {

    private ResponsePayloadUtil() {
    }

    public static <T> T getFirstData(
            final List<T> data,
            final String mensaje
    ) throws EBusinessException {

        if (data == null || data.isEmpty() || data.get(0) == null) {
            throw new EBusinessException(mensaje);
        }

        return data.get(0);
    }

    public static <T> List<T> getData(
            final List<T> data,
            final String mensaje
    ) throws EBusinessException {

        if (data == null || data.isEmpty()) {
            throw new EBusinessException(mensaje);
        }

        return data;
    }
}