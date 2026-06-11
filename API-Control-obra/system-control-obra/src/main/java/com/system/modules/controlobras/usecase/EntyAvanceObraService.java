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
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsplamaplandetrabajo;
import com.system.crosscutting.persistence.entity.EntyOrsplamdplantrabsemana;
import com.system.crosscutting.persistence.repository.EntyOrsplamaplandetrabajoRepository;
import com.system.crosscutting.persistence.repository.EntyOrsplamdplantrabsemanaRepository;
import com.system.crosscutting.persistence.repository.EntyOrsplamdreportediarioRepository;

@Service
public class EntyAvanceObraService {

    private static final String APP_KEY = "CONTROL_OBRAS";

    private static final String ESTADO_SIN_AVANCE = "SIN_AVANCE";
    private static final String ESTADO_COMPLETADO = "COMPLETADO";
    private static final String ESTADO_AVANCE_ALTO = "AVANCE_ALTO";
    private static final String ESTADO_EN_EJECUCION = "EN_EJECUCION";

    @Autowired
    private EntyOrsplamdplantrabsemanaRepository planSemanaRepository;

    @Autowired
    private EntyOrsplamaplandetrabajoRepository planRepository;

    @Autowired
    private EntyOrsplamdreportediarioRepository reporteRepository;

    public AvanceObraResponse getAvanceByOrden(final String ordenKey)
            throws EBusinessException {

        validarTextoObligatorio(ordenKey, "El código de la orden es obligatorio.");

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

    public AvanceObraResponse getAvanceByPlan(final String planKey)
            throws EBusinessException {

        validarTextoObligatorio(planKey, "El código del plan de trabajo es obligatorio.");

        List<EntyOrsplamdplantrabsemana> planesSemana =
                planSemanaRepository.findByOrsIdentifkeyPltr(planKey);

        List<AvanceObraDto> data = planesSemana.stream()
                .map(this::construirAvancePlanSemana)
                .collect(Collectors.toList());

        AvanceObraDto totalPlan = construirTotalPlan(planKey, planesSemana);
        data.add(0, totalPlan);

        return buildResponse(planKey, data);
    }

    public AvanceObraResponse getAvanceByPlanSemanal(final String planSemanalKey)
            throws EBusinessException {

        validarTextoObligatorio(planSemanalKey, "El código del plan semanal es obligatorio.");

        EntyOrsplamdplantrabsemana planSemana =
                planSemanaRepository.findByOrsIdentifkeyPlse(planSemanalKey)
                        .orElseThrow(() -> new EBusinessException(
                                "No existe el plan semanal: " + planSemanalKey
                        ));

        AvanceObraDto dto = construirAvancePlanSemana(planSemana);

        return buildResponse(planSemanalKey, List.of(dto));
    }

    public AvanceObraResponse calcularAvancePorOrden(final String codigoOrden)
            throws EBusinessException {
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

        cargarDatosPlanTrabajo(dto, planSemana.getOrsIdentifkeyPltr());

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

        BigDecimal cantidadPlaneadaTotal = sumarCantidadPlaneada(planesSemana);
        BigDecimal cantidadEjecutadaTotal = sumarCantidadEjecutada(planesSemana);
        BigDecimal saldoPendienteTotal = cantidadPlaneadaTotal.subtract(cantidadEjecutadaTotal);
        BigDecimal porcentajeTotal = calcularPorcentaje(cantidadPlaneadaTotal, cantidadEjecutadaTotal);

        BigDecimal valorPlaneadoTotal = sumarValorPlaneado(planesSemana);
        BigDecimal valorEjecutadoTotal = sumarValorEjecutado(planesSemana);
        BigDecimal saldoValorTotal = valorPlaneadoTotal.subtract(valorEjecutadoTotal);

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

        dto.setValorPlaneadoSemana(valorPlaneadoTotal);
        dto.setValorEjecutadoSemana(valorEjecutadoTotal);
        dto.setSaldoValorSemana(saldoValorTotal);

        dto.setEstadoAvance(calcularEstadoAvance(porcentajeTotal));

        return dto;
    }

    private AvanceObraDto construirTotalPlan(
            final String planKey,
            final List<EntyOrsplamdplantrabsemana> planesSemana
    ) {

        BigDecimal cantidadPlaneadaTotal = sumarCantidadPlaneada(planesSemana);
        BigDecimal cantidadEjecutadaTotal = sumarCantidadEjecutada(planesSemana);
        BigDecimal saldoPendienteTotal = cantidadPlaneadaTotal.subtract(cantidadEjecutadaTotal);
        BigDecimal porcentajeTotal = calcularPorcentaje(cantidadPlaneadaTotal, cantidadEjecutadaTotal);

        BigDecimal valorPlaneadoTotal = sumarValorPlaneado(planesSemana);
        BigDecimal valorEjecutadoTotal = sumarValorEjecutado(planesSemana);
        BigDecimal saldoValorTotal = valorPlaneadoTotal.subtract(valorEjecutadoTotal);

        AvanceObraDto dto = new AvanceObraDto();

        dto.setOrsIdentifkeyPltr(planKey);
        dto.setDescripcionActividad("TOTAL PLAN");

        if (!planesSemana.isEmpty()) {
            dto.setOrsIdentifkeyOrde(planesSemana.get(0).getOrsIdentifkeyOrde());
            dto.setOrsIdentifkeyPsem(planesSemana.get(0).getOrsIdentifkeyPsem());
        }

        dto.setCantidadPlaneadaTotal(cantidadPlaneadaTotal);
        dto.setCantidadEjecutadaTotal(cantidadEjecutadaTotal);
        dto.setSaldoPendienteTotal(saldoPendienteTotal);
        dto.setPorcentajeAvanceTotal(porcentajeTotal);

        dto.setCantidadPlaneadaSemana(cantidadPlaneadaTotal);
        dto.setCantidadEjecutadaSemana(cantidadEjecutadaTotal);
        dto.setSaldoPendienteSemana(saldoPendienteTotal);
        dto.setPorcentajeAvanceSemana(porcentajeTotal);

        dto.setValorPlaneadoSemana(valorPlaneadoTotal);
        dto.setValorEjecutadoSemana(valorEjecutadoTotal);
        dto.setSaldoValorSemana(saldoValorTotal);

        dto.setEstadoAvance(calcularEstadoAvance(porcentajeTotal));

        return dto;
    }

    private void cargarDatosPlanTrabajo(
            final AvanceObraDto dto,
            final String planTrabajoKey
    ) {

        if (planTrabajoKey == null || planTrabajoKey.trim().isEmpty()) {
            return;
        }

        EntyOrsplamaplandetrabajo plan =
                planRepository.findByOrsIdentifkeyPltr(planTrabajoKey)
                        .orElse(null);

        if (plan != null) {
            dto.setDescripcionActividad(plan.getOrsDesactividadPltr());
            dto.setEquipoInventario(plan.getPrvIdentifkeyInve());
        }
    }

    private BigDecimal sumarCantidadPlaneada(
            final List<EntyOrsplamdplantrabsemana> planesSemana
    ) {
        return planesSemana.stream()
                .map(EntyOrsplamdplantrabsemana::getOrsCantidunidadPlse)
                .filter(valor -> valor != null)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumarCantidadEjecutada(
            final List<EntyOrsplamdplantrabsemana> planesSemana
    ) {
        return planesSemana.stream()
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
    }

    private BigDecimal sumarValorPlaneado(
            final List<EntyOrsplamdplantrabsemana> planesSemana
    ) {
        return planesSemana.stream()
                .map(EntyOrsplamdplantrabsemana::getOrsValortotalPlse)
                .filter(valor -> valor != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal sumarValorEjecutado(
            final List<EntyOrsplamdplantrabsemana> planesSemana
    ) {
        return planesSemana.stream()
                .map(plan -> {
                    Integer ejecutado = reporteRepository.sumEjecutadoByPlanSemana(
                            plan.getOrsIdentifkeyPlse()
                    );

                    if (ejecutado == null) {
                        ejecutado = nvlInteger(plan.getOrsEjecutunidadPlse());
                    }

                    BigDecimal valorUnidad = nvlBigDecimal(plan.getOrsValorunidadPlse());

                    return valorUnidad.multiply(BigDecimal.valueOf(ejecutado));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private AvanceObraResponse buildResponse(
            final String parentKey,
            final List<AvanceObraDto> data
    ) {
        AvanceObraResponse response = new AvanceObraResponse();

        response.setRspMessage("OK");
        response.setRspValue("OK");
        response.setRspParentKey(parentKey);
        response.setRspAppKey(APP_KEY);
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
            return ESTADO_SIN_AVANCE;
        }

        if (porcentajeAvance.compareTo(BigDecimal.valueOf(100)) >= 0) {
            return ESTADO_COMPLETADO;
        }

        if (porcentajeAvance.compareTo(BigDecimal.valueOf(50)) >= 0) {
            return ESTADO_AVANCE_ALTO;
        }

        return ESTADO_EN_EJECUCION;
    }

    private void validarTextoObligatorio(
            final String valor,
            final String mensaje
    ) throws EBusinessException {

        if (valor == null || valor.trim().isEmpty()) {
            throw new EBusinessException(mensaje);
        }
    }

    private Integer nvlInteger(final Integer value) {
        return value == null ? 0 : value;
    }

    private BigDecimal nvlBigDecimal(final BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}