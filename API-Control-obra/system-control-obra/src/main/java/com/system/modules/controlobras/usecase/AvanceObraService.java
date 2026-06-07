package com.system.modules.controlobras.usecase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.AvanceObraDto;
import com.system.crosscutting.domain.model.AvanceObraResponse;
import com.system.crosscutting.persistence.entity.EntyOrsplamaplandetrabajo;
import com.system.crosscutting.persistence.entity.EntyOrsplamdplantrabsemana;
import com.system.crosscutting.persistence.repository.EntyOrsplamaplandetrabajoRepository;
import com.system.crosscutting.persistence.repository.EntyOrsplamdplantrabsemanaRepository;
import com.system.crosscutting.persistence.repository.EntyOrsplamdreportediarioRepository;

@Service
public class AvanceObraService {

    @Autowired
    private EntyOrsplamdplantrabsemanaRepository planSemanaRepository;

    @Autowired
    private EntyOrsplamaplandetrabajoRepository planRepository;

    @Autowired
    private EntyOrsplamdreportediarioRepository reporteRepository;

    /**
     * Nuevo endpoint:
     * GET /api/control-obras/avances/by-orden?ordenKey=ORS-001
     */
    public AvanceObraResponse getAvanceByOrden(final String ordenKey) {
        List<EntyOrsplamdplantrabsemana> planesSemana =
                planSemanaRepository.searchByOrden(ordenKey, Pageable.unpaged())
                        .getContent();

        List<AvanceObraDto> data = planesSemana.stream()
                .map(this::construirAvancePlanSemana)
                .collect(Collectors.toList());

        AvanceObraDto totalOrden = construirTotalOrden(ordenKey, planesSemana);

        data.add(0, totalOrden);

        return buildResponse(ordenKey, data);
    }

    /**
     * Nuevo endpoint:
     * GET /api/control-obras/avances/by-plan?planKey=PLTR-001
     */
    public AvanceObraResponse getAvanceByPlan(final String planKey) {
        List<EntyOrsplamdplantrabsemana> planesSemana =
                planSemanaRepository.findByOrsIdentifkeyPltr(planKey);

        List<AvanceObraDto> data = planesSemana.stream()
                .map(this::construirAvancePlanSemana)
                .collect(Collectors.toList());

        AvanceObraDto totalPlan = construirTotalPlan(planKey, planesSemana);

        data.add(0, totalPlan);

        return buildResponse(planKey, data);
    }

    /**
     * Nuevo endpoint:
     * GET /api/control-obras/avances/by-plan-semanal?planSemanalKey=PLSE-001
     */
    public AvanceObraResponse getAvanceByPlanSemanal(final String planSemanalKey) {
        EntyOrsplamdplantrabsemana planSemana =
                planSemanaRepository.findByOrsIdentifkeyPlse(planSemanalKey)
                        .orElseThrow(() -> new IllegalArgumentException(
                                "No existe el plan semanal " + planSemanalKey
                        ));

        AvanceObraDto dto = construirAvancePlanSemana(planSemana);

        return buildResponse(planSemanalKey, List.of(dto));
    }

    /**
     * Método antiguo conservado para compatibilidad con:
     * GET /api/control-obras/avances/orden/{codigoOrden}
     */
    public AvanceObraResponse calcularAvancePorOrden(final String codigoOrden) {
        return getAvanceByOrden(codigoOrden);
    }

    private AvanceObraDto construirAvancePlanSemana(
            final EntyOrsplamdplantrabsemana planSemana
    ) {
        Integer planeado = nvlInteger(planSemana.getOrsCantidunidadPlse());

        Integer ejecutado = reporteRepository.sumEjecutadoByPlanSemana(
                planSemana.getOrsIdentifkeyPlse()
        );

        if (ejecutado == null) {
            ejecutado = nvlInteger(planSemana.getOrsEjecutunidadPlse());
        }

        Integer saldo = planeado - ejecutado;

        BigDecimal porcentaje = calcularPorcentaje(
                BigDecimal.valueOf(planeado),
                BigDecimal.valueOf(ejecutado)
        );

        BigDecimal valorUnidad = nvlBigDecimal(planSemana.getOrsValorunidadPlse());
        BigDecimal valorPlaneado = nvlBigDecimal(planSemana.getOrsValortotalPlse());
        BigDecimal valorEjecutado = valorUnidad.multiply(BigDecimal.valueOf(ejecutado));
        BigDecimal saldoValor = valorPlaneado.subtract(valorEjecutado);

        AvanceObraDto dto = new AvanceObraDto();

        dto.setOrsIdentifkeyOrde(planSemana.getOrsIdentifkeyOrde());
        dto.setOrsIdentifkeyPltr(planSemana.getOrsIdentifkeyPltr());
        dto.setOrsIdentifkeyPlse(planSemana.getOrsIdentifkeyPlse());
        dto.setOrsIdentifkeyPsem(planSemana.getOrsIdentifkeyPsem());

        EntyOrsplamaplandetrabajo plan =
                planRepository.findByOrsIdentifkeyPltr(planSemana.getOrsIdentifkeyPltr())
                        .orElse(null);

        if (plan != null) {
            dto.setDescripcionActividad(plan.getOrsDesactividadPltr());
            dto.setEquipoInventario(plan.getPrvIdentifkeyInve());
        }

        dto.setCantidadPlaneadaSemana(BigDecimal.valueOf(planeado));
        dto.setCantidadEjecutadaSemana(BigDecimal.valueOf(ejecutado));
        dto.setSaldoPendienteSemana(BigDecimal.valueOf(saldo));
        dto.setPorcentajeAvanceSemana(porcentaje);

        dto.setValorPlaneadoSemana(valorPlaneado);
        dto.setValorEjecutadoSemana(valorEjecutado);
        dto.setSaldoValorSemana(saldoValor);

        dto.setEstadoAvance(calcularEstadoAvance(porcentaje));

        return dto;
    }

    private AvanceObraDto construirTotalOrden(
            final String ordenKey,
            final List<EntyOrsplamdplantrabsemana> planesSemana
    ) {
        BigDecimal cantidadPlaneadaTotal = planesSemana.stream()
                .map(EntyOrsplamdplantrabsemana::getOrsCantidunidadPlse)
                .filter(valor -> valor != null)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal cantidadEjecutadaTotal = planesSemana.stream()
                .map(plan -> {
                    Integer ejecutado = reporteRepository.sumEjecutadoByPlanSemana(
                            plan.getOrsIdentifkeyPlse()
                    );

                    if (ejecutado == null) {
                        ejecutado = nvlInteger(plan.getOrsEjecutunidadPlse());
                    }

                    return BigDecimal.valueOf(ejecutado);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoPendienteTotal = cantidadPlaneadaTotal.subtract(cantidadEjecutadaTotal);
        BigDecimal porcentajeTotal = calcularPorcentaje(cantidadPlaneadaTotal, cantidadEjecutadaTotal);

        AvanceObraDto dto = new AvanceObraDto();

        dto.setOrsIdentifkeyOrde(ordenKey);
        dto.setDescripcionActividad("TOTAL ORDEN");

        dto.setCantidadPlaneadaTotal(cantidadPlaneadaTotal);
        dto.setCantidadEjecutadaTotal(cantidadEjecutadaTotal);
        dto.setSaldoPendienteTotal(saldoPendienteTotal);
        dto.setPorcentajeAvanceTotal(porcentajeTotal);

        dto.setCantidadPlaneadaSemana(cantidadPlaneadaTotal);
        dto.setCantidadEjecutadaSemana(cantidadEjecutadaTotal);
        dto.setSaldoPendienteSemana(saldoPendienteTotal);
        dto.setPorcentajeAvanceSemana(porcentajeTotal);

        dto.setEstadoAvance(calcularEstadoAvance(porcentajeTotal));

        return dto;
    }

    private AvanceObraDto construirTotalPlan(
            final String planKey,
            final List<EntyOrsplamdplantrabsemana> planesSemana
    ) {
        BigDecimal cantidadPlaneadaTotal = planesSemana.stream()
                .map(EntyOrsplamdplantrabsemana::getOrsCantidunidadPlse)
                .filter(valor -> valor != null)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal cantidadEjecutadaTotal = planesSemana.stream()
                .map(plan -> {
                    Integer ejecutado = reporteRepository.sumEjecutadoByPlanSemana(
                            plan.getOrsIdentifkeyPlse()
                    );

                    if (ejecutado == null) {
                        ejecutado = nvlInteger(plan.getOrsEjecutunidadPlse());
                    }

                    return BigDecimal.valueOf(ejecutado);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal saldoPendienteTotal = cantidadPlaneadaTotal.subtract(cantidadEjecutadaTotal);
        BigDecimal porcentajeTotal = calcularPorcentaje(cantidadPlaneadaTotal, cantidadEjecutadaTotal);

        AvanceObraDto dto = new AvanceObraDto();

        dto.setOrsIdentifkeyPltr(planKey);
        dto.setDescripcionActividad("TOTAL PLAN");

        if (!planesSemana.isEmpty()) {
            dto.setOrsIdentifkeyOrde(planesSemana.get(0).getOrsIdentifkeyOrde());
        }

        dto.setCantidadPlaneadaTotal(cantidadPlaneadaTotal);
        dto.setCantidadEjecutadaTotal(cantidadEjecutadaTotal);
        dto.setSaldoPendienteTotal(saldoPendienteTotal);
        dto.setPorcentajeAvanceTotal(porcentajeTotal);

        dto.setCantidadPlaneadaSemana(cantidadPlaneadaTotal);
        dto.setCantidadEjecutadaSemana(cantidadEjecutadaTotal);
        dto.setSaldoPendienteSemana(saldoPendienteTotal);
        dto.setPorcentajeAvanceSemana(porcentajeTotal);

        dto.setEstadoAvance(calcularEstadoAvance(porcentajeTotal));

        return dto;
    }

    private AvanceObraResponse buildResponse(
            final String parentKey,
            final List<AvanceObraDto> data
    ) {
        AvanceObraResponse response = new AvanceObraResponse();

        response.setRspMessage("OK");
        response.setRspValue("OK");
        response.setRspParentKey(parentKey);
        response.setRspAppKey("CONTROL_OBRAS");
        response.setRspData(data);

        return response;
    }

    private BigDecimal calcularPorcentaje(
            final BigDecimal planeado,
            final BigDecimal ejecutado
    ) {
        if (planeado == null || planeado.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        if (ejecutado == null) {
            return BigDecimal.ZERO;
        }

        return ejecutado
                .multiply(BigDecimal.valueOf(100))
                .divide(planeado, 2, RoundingMode.HALF_UP);
    }

    private String calcularEstadoAvance(final BigDecimal porcentajeAvance) {
        if (porcentajeAvance == null || porcentajeAvance.compareTo(BigDecimal.ZERO) <= 0) {
            return "SIN_AVANCE";
        }

        if (porcentajeAvance.compareTo(BigDecimal.valueOf(100)) >= 0) {
            return "COMPLETADO";
        }

        if (porcentajeAvance.compareTo(BigDecimal.valueOf(50)) >= 0) {
            return "AVANCE_ALTO";
        }

        return "EN_EJECUCION";
    }

    private Integer nvlInteger(final Integer value) {
        return value == null ? 0 : value;
    }

    private BigDecimal nvlBigDecimal(final BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}