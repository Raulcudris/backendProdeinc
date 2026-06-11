package com.system.crosscutting.domain.constants;
import java.util.Set;

public final class TipoRegistroEvidenciaConstants {

    private TipoRegistroEvidenciaConstants() {
    }

    public static final Set<String> TIPOS_VALIDOS = Set.of(
            "REPORTE_OPERACION",
            "NOVEDAD",
            "DETALLE_EQUIPO_OPERACION",
            "INFORME_SEMANAL",
            "ACTA_MODIFICACION",
            "DETALLE_ACTA_MODIFICACION",
            "ORDEN_SERVICIO",
            "SITIO_PUNTO",
            "PLAN_TRABAJO",
            "PLAN_SEMANAL"
    );

    public static boolean isValid(final String tipoRegistro) {
        return tipoRegistro != null
                && TIPOS_VALIDOS.contains(tipoRegistro.trim().toUpperCase());
    }

    public static String normalize(final String tipoRegistro) {
        return tipoRegistro == null ? null : tipoRegistro.trim().toUpperCase();
    }
}