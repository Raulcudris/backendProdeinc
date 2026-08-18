package com.system.modules.workcontrol.usecase;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.AvanceOrdenDetalleDto;
import com.system.crosscutting.domain.model.AvanceOrdenDetalleResponse;
import com.system.crosscutting.domain.model.AvanceOrdenDto;
import com.system.crosscutting.domain.model.AvanceOrdenResponse;
import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.dataproviders.IjpaPlanTrabajoSemanaDataProviders;
import com.system.modules.workcontrol.dataproviders.IjpaReporteDiarioDataProviders;

@Service
public class ReporteAvanceObraService {

    private final IjpaPlanTrabajoSemanaDataProviders planSemanaDataProviders;
    private final IjpaReporteDiarioDataProviders reporteDiarioDataProviders;

    public ReporteAvanceObraService(
            final IjpaPlanTrabajoSemanaDataProviders planSemanaDataProviders,
            final IjpaReporteDiarioDataProviders reporteDiarioDataProviders
    ) {
        this.planSemanaDataProviders = planSemanaDataProviders;
        this.reporteDiarioDataProviders = reporteDiarioDataProviders;
    }

    public AvanceOrdenResponse getAvancePorOrden(
            final String ordenKey
    ) throws EBusinessException {

        String key = validarOrdenKey(ordenKey);

        List<EntyOrsplamdplantrabsemanaDto> planesSemana =
                planSemanaDataProviders.findByOrden(key);

        List<EntyOrsplamdreportediarioDto> reportesDiarios =
                reporteDiarioDataProviders.findByOrden(key);

        BigDecimal planificado = sumarPlanificado(planesSemana);
        BigDecimal ejecutado = sumarEjecutado(reportesDiarios);

        BigDecimal saldo = planificado.subtract(ejecutado);
        BigDecimal porcentaje = calcularPorcentaje(ejecutado, planificado);

        AvanceOrdenDto dto = AvanceOrdenDto
                .builder()
                .ordenKey(key)
                .cantidadPlanificada(planificado)
                .cantidadEjecutada(ejecutado)
                .cantidadSaldo(saldo)
                .porcentajeAvance(porcentaje)
                .build();

        AvanceOrdenResponse response = new AvanceOrdenResponse();
        response.setRspValue("OK");
        response.setRspMessage("Avance por orden consultado correctamente.");
        response.setRspParentKey(key);
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(Collections.singletonList(dto));

        return response;
    }

    public AvanceOrdenDetalleResponse getDetalleAvancePorOrden(
            final String ordenKey
    ) throws EBusinessException {

        String key = validarOrdenKey(ordenKey);

        List<EntyOrsplamdplantrabsemanaDto> planesSemana =
                planSemanaDataProviders.findByOrden(key);

        List<EntyOrsplamdreportediarioDto> reportesDiarios =
                reporteDiarioDataProviders.findByOrden(key);

        List<AvanceOrdenDetalleDto> detalle = new ArrayList<>();

        if (planesSemana != null) {
            for (EntyOrsplamdplantrabsemanaDto planSemana : planesSemana) {
                String planSemanaKey = obtenerPlanSemanaKey(planSemana);
                String semanaKey = obtenerSemanaKey(planSemana);

                BigDecimal planificado = obtenerCantidadPlanificada(planSemana);
                BigDecimal ejecutado = sumarEjecutadoRelacionado(
                        reportesDiarios,
                        planSemanaKey,
                        semanaKey
                );

                BigDecimal saldo = planificado.subtract(ejecutado);
                BigDecimal porcentaje = calcularPorcentaje(
                        ejecutado,
                        planificado
                );

                AvanceOrdenDetalleDto dto = AvanceOrdenDetalleDto
                        .builder()
                        .ordenKey(key)
                        .planSemanaKey(planSemanaKey)
                        .semanaKey(semanaKey)
                        .cantidadPlanificada(planificado)
                        .cantidadEjecutada(ejecutado)
                        .cantidadSaldo(saldo)
                        .porcentajeAvance(porcentaje)
                        .build();

                detalle.add(dto);
            }
        }

        AvanceOrdenDetalleResponse response =
                new AvanceOrdenDetalleResponse();

        response.setRspValue("OK");
        response.setRspMessage(
                "Detalle de avance por orden consultado correctamente."
        );
        response.setRspParentKey(key);
        response.setRspAppKey("WORK-CONTROL");
        response.setRspData(detalle);

        return response;
    }

    private String validarOrdenKey(
            final String ordenKey
    ) {
        if (ordenKey == null || ordenKey.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código de la orden de servicio es obligatorio."
            );
        }

        return ordenKey.trim().toUpperCase();
    }

    private BigDecimal sumarPlanificado(
            final Collection<EntyOrsplamdplantrabsemanaDto> items
    ) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (EntyOrsplamdplantrabsemanaDto item : items) {
            total = total.add(obtenerCantidadPlanificada(item));
        }

        return total;
    }

    private BigDecimal sumarEjecutado(
            final Collection<EntyOrsplamdreportediarioDto> items
    ) {
        if (items == null || items.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (EntyOrsplamdreportediarioDto item : items) {
            total = total.add(obtenerCantidadEjecutada(item));
        }

        return total;
    }

    private BigDecimal sumarEjecutadoRelacionado(
            final Collection<EntyOrsplamdreportediarioDto> reportes,
            final String planSemanaKey,
            final String semanaKey
    ) {
        if (reportes == null || reportes.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;

        for (EntyOrsplamdreportediarioDto reporte : reportes) {
            if (perteneceReporteAPlan(reporte, planSemanaKey, semanaKey)) {
                total = total.add(obtenerCantidadEjecutada(reporte));
            }
        }

        return total;
    }

    private boolean perteneceReporteAPlan(
            final EntyOrsplamdreportediarioDto reporte,
            final String planSemanaKey,
            final String semanaKey
    ) {
        String reportePlanSemanaKey = obtenerPlanSemanaKey(reporte);
        String reporteSemanaKey = obtenerSemanaKey(reporte);

        if (planSemanaKey != null
                && reportePlanSemanaKey != null
                && planSemanaKey.equalsIgnoreCase(reportePlanSemanaKey)) {
            return true;
        }

        return semanaKey != null
                && reporteSemanaKey != null
                && semanaKey.equalsIgnoreCase(reporteSemanaKey);
    }

    private BigDecimal obtenerCantidadPlanificada(
            final Object item
    ) {
        BigDecimal valor = obtenerValorPorGetters(
                item,
                Arrays.asList(
                        "getOrsCantidprogrPlse",
                        "getOrsCantprogrPlse",
                        "getOrsCantProgPlse",
                        "getOrsCantidadprogrPlse",
                        "getOrsCantidadProgPlse",
                        "getOrsCantidadprogramadaPlse",
                        "getOrsCantidadProgramadaPlse",
                        "getOrsCantprogramadaPlse",
                        "getOrsCantProgramadaPlse",
                        "getOrsCantidadplanificadaPlse",
                        "getOrsCantidadPlanificadaPlse",
                        "getOrsCantplanificadaPlse",
                        "getOrsCantPlanificadaPlse",
                        "getOrsCantidadPlse",
                        "getOrsCantPlse"
                )
        );

        if (valor != null) {
            return valor;
        }

        valor = obtenerValorPorBusqueda(
                item,
                "plse",
                Arrays.asList(
                        "cant",
                        "cantidad",
                        "program",
                        "progr",
                        "planificada",
                        "planeada"
                )
        );

        return valor != null ? valor : BigDecimal.ZERO;
    }

    private BigDecimal obtenerCantidadEjecutada(
            final Object item
    ) {
        BigDecimal valor = obtenerValorPorGetters(
                item,
                Arrays.asList(
                        "getOrsEjecutunidadPdia",
                        "getOrsEjecutUnidadPdia",
                        "getOrsCantidadejecutadaPdia",
                        "getOrsCantidadEjecutadaPdia",
                        "getOrsCantejecutadaPdia",
                        "getOrsCantEjecutadaPdia",
                        "getOrsCantiejecPdia",
                        "getOrsCantidadreportadaPdia",
                        "getOrsCantidadReportadaPdia",
                        "getOrsCantidadPdia",
                        "getOrsCantPdia",
                        "getOrsAvanceejecutadoPdia",
                        "getOrsAvanceEjecutadoPdia"
                )
        );

        if (valor != null) {
            return valor;
        }

        valor = obtenerValorPorBusqueda(
                item,
                "pdia",
                Arrays.asList(
                        "ejecut",
                        "cant",
                        "cantidad",
                        "avance",
                        "report"
                )
        );

        return valor != null ? valor : BigDecimal.ZERO;
    }

    private String obtenerPlanSemanaKey(
            final Object item
    ) {
        String valor = obtenerTextoPorGetters(
                item,
                Arrays.asList(
                        "getOrsIdentifkeyPlse",
                        "getOrsIdentifkeyPlanSemana",
                        "getOrsIdentifkeyPlansemana",
                        "getOrsIdentifkeyPlantrabsemana",
                        "getOrsIdentifkeyPlanTrabSemana"
                )
        );

        if (valor != null) {
            return valor;
        }

        return obtenerTextoPorBusqueda(
                item,
                Arrays.asList("identifkey"),
                Arrays.asList("plse")
        );
    }

    private String obtenerSemanaKey(
            final Object item
    ) {
        String valor = obtenerTextoPorGetters(
                item,
                Arrays.asList(
                        "getOrsIdentifkeyDips",
                        "getOrsIdentifkeySemana",
                        "getOrsIdentifkeySema",
                        "getOrsIdentifkeyPsem",
                        "getOrsIdentifkeyPrse",
                        "getOrsIdentifkeyProyecsemana",
                        "getOrsIdentifkeyProySemana"
                )
        );

        if (valor != null) {
            return valor;
        }

        return obtenerTextoPorBusqueda(
                item,
                Arrays.asList("identifkey"),
                Arrays.asList("dips", "semana", "sema", "psem", "prse")
        );
    }

    private BigDecimal calcularPorcentaje(
            final BigDecimal ejecutado,
            final BigDecimal planificado
    ) {
        if (planificado == null
                || planificado.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal safeEjecutado =
                ejecutado != null ? ejecutado : BigDecimal.ZERO;

        return safeEjecutado
                .multiply(BigDecimal.valueOf(100))
                .divide(planificado, 2, RoundingMode.HALF_UP);
    }

    private BigDecimal obtenerValorPorGetters(
            final Object item,
            final List<String> getters
    ) {
        if (item == null || getters == null || getters.isEmpty()) {
            return null;
        }

        for (String getter : getters) {
            try {
                Method method = item.getClass().getMethod(getter);
                Object value = method.invoke(item);
                BigDecimal decimal = convertirBigDecimal(value);

                if (decimal != null) {
                    return decimal;
                }
            } catch (ReflectiveOperationException ignored) {
                // Continúa buscando otro getter compatible.
            }
        }

        return null;
    }

    private BigDecimal obtenerValorPorBusqueda(
            final Object item,
            final String sufijoEntidad,
            final List<String> tokensBusqueda
    ) {
        if (item == null) {
            return null;
        }

        Method[] methods = item.getClass().getMethods();

        for (Method method : methods) {
            if (method.getParameterCount() > 0) {
                continue;
            }

            String methodName = method.getName();

            if (!methodName.startsWith("get")) {
                continue;
            }

            String normalized = methodName.toLowerCase();

            if (!normalized.contains(sufijoEntidad.toLowerCase())) {
                continue;
            }

            if (esGetterNoValidoParaCantidad(normalized)) {
                continue;
            }

            boolean coincideToken = false;

            for (String token : tokensBusqueda) {
                if (normalized.contains(token.toLowerCase())) {
                    coincideToken = true;
                    break;
                }
            }

            if (!coincideToken) {
                continue;
            }

            try {
                Object value = method.invoke(item);
                BigDecimal decimal = convertirBigDecimal(value);

                if (decimal != null) {
                    return decimal;
                }
            } catch (ReflectiveOperationException ignored) {
                // Continúa buscando otro getter compatible.
            }
        }

        return null;
    }

    private String obtenerTextoPorGetters(
            final Object item,
            final List<String> getters
    ) {
        if (item == null || getters == null || getters.isEmpty()) {
            return null;
        }

        for (String getter : getters) {
            try {
                Method method = item.getClass().getMethod(getter);
                Object value = method.invoke(item);
                String texto = convertirTexto(value);

                if (texto != null) {
                    return texto;
                }
            } catch (ReflectiveOperationException ignored) {
                // Continúa buscando otro getter compatible.
            }
        }

        return null;
    }

    private String obtenerTextoPorBusqueda(
            final Object item,
            final List<String> tokensObligatorios,
            final List<String> tokensOpcionales
    ) {
        if (item == null) {
            return null;
        }

        Method[] methods = item.getClass().getMethods();

        for (Method method : methods) {
            if (method.getParameterCount() > 0) {
                continue;
            }

            String methodName = method.getName();

            if (!methodName.startsWith("get")) {
                continue;
            }

            String normalized = methodName.toLowerCase();

            boolean obligatoriosOk = true;

            for (String token : tokensObligatorios) {
                if (!normalized.contains(token.toLowerCase())) {
                    obligatoriosOk = false;
                    break;
                }
            }

            if (!obligatoriosOk) {
                continue;
            }

            boolean opcionalOk = false;

            for (String token : tokensOpcionales) {
                if (normalized.contains(token.toLowerCase())) {
                    opcionalOk = true;
                    break;
                }
            }

            if (!opcionalOk) {
                continue;
            }

            try {
                Object value = method.invoke(item);
                String texto = convertirTexto(value);

                if (texto != null) {
                    return texto;
                }
            } catch (ReflectiveOperationException ignored) {
                // Continúa buscando otro getter compatible.
            }
        }

        return null;
    }

    private boolean esGetterNoValidoParaCantidad(
            final String methodName
    ) {
        return methodName.contains("primarykey")
                || methodName.contains("identifkey")
                || methodName.contains("estadoreg")
                || methodName.contains("tiporegist")
                || methodName.contains("fecha")
                || methodName.contains("orden")
                || methodName.contains("semana")
                || methodName.contains("plan")
                || methodName.contains("descripcion")
                || methodName.contains("observacion")
                || methodName.contains("usuario");
    }

    private BigDecimal convertirBigDecimal(
            final Object value
    ) {
        if (value == null) {
            return null;
        }

        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }

        if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        }

        if (value instanceof Integer) {
            return BigDecimal.valueOf(((Integer) value).longValue());
        }

        if (value instanceof Long) {
            return BigDecimal.valueOf((Long) value);
        }

        if (value instanceof Double) {
            return BigDecimal.valueOf((Double) value);
        }

        if (value instanceof Float) {
            return BigDecimal.valueOf(((Float) value).doubleValue());
        }

        if (value instanceof Short) {
            return BigDecimal.valueOf(((Short) value).longValue());
        }

        if (value instanceof String) {
            String clean = ((String) value).trim();

            if (clean.isEmpty()) {
                return null;
            }

            try {
                return new BigDecimal(clean);
            } catch (NumberFormatException ignored) {
                return null;
            }
        }

        return null;
    }

    private String convertirTexto(
            final Object value
    ) {
        if (value == null) {
            return null;
        }

        String text = String.valueOf(value).trim();

        return text.isEmpty() ? null : text.toUpperCase();
    }
}