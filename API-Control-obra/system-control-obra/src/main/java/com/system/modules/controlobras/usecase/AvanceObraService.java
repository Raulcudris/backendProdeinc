package com.system.modules.controlobras.usecase;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.AvanceObraDto;
import com.system.crosscutting.domain.model.AvanceObraResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.entity.EntyOrsplnmaplantrabajo;
import com.system.crosscutting.persistence.entity.EntyOrspspmdplansemanal;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.crosscutting.persistence.repository.EntyOrsplnmaplantrabajoRepository;
import com.system.crosscutting.persistence.repository.EntyOrspspmdplansemanalRepository;
import com.system.crosscutting.persistence.repository.EntyOrsrdomdreporteDiarioRepository;
import com.system.modules.controlobras.services.UseCase;

@UseCase
public class AvanceObraService {

    @Autowired
    private EntyOrsordmaordenservicioRepository ordenRepository;

    @Autowired
    private EntyOrsplnmaplantrabajoRepository planTrabajoRepository;

    @Autowired
    private EntyOrspspmdplansemanalRepository planSemanalRepository;

    @Autowired
    private EntyOrsrdomdreporteDiarioRepository reporteDiarioRepository;

    public AvanceObraResponse getAvanceByOrden(
            String ordenKey
    ) throws EBusinessException {

        if (ordenKey == null || ordenKey.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código de la orden de servicio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (ordenRepository.findByOrsIdentifkeyOrde(ordenKey).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe la orden de servicio con el código " + ordenKey)
                    .withCode("404")
                    .buildBusinessException();
        }

        List<EntyOrsplnmaplantrabajo> planes = planTrabajoRepository.findActiveByOrden(ordenKey);

        List<AvanceObraDto> avances = new ArrayList<>();

        for (EntyOrsplnmaplantrabajo plan : planes) {
            avances.add(calcularAvancePlan(plan));
        }

        AvanceObraResponse response = new AvanceObraResponse();
        response.setRspMessage("OK");
        response.setRspValue("OK");
        response.setRspParentKey(ordenKey);
        response.setRspAppKey("msvc-control-obras");
        response.setRspData(avances);

        return response;
    }

    public AvanceObraResponse getAvanceByPlan(
            String planKey
    ) throws EBusinessException {

        if (planKey == null || planKey.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del plan de trabajo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrsplnmaplantrabajo plan = planTrabajoRepository
                .findByOrsIdentifkeyPltr(planKey)
                .orElse(null);

        if (plan == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el plan de trabajo con el código " + planKey)
                    .withCode("404")
                    .buildBusinessException();
        }

        List<AvanceObraDto> avances = new ArrayList<>();
        avances.add(calcularAvancePlan(plan));

        AvanceObraResponse response = new AvanceObraResponse();
        response.setRspMessage("OK");
        response.setRspValue("OK");
        response.setRspParentKey(planKey);
        response.setRspAppKey("msvc-control-obras");
        response.setRspData(avances);

        return response;
    }

    public AvanceObraResponse getAvanceByPlanSemanal(
            String planSemanalKey
    ) throws EBusinessException {

        if (planSemanalKey == null || planSemanalKey.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del plan semanal es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrspspmdplansemanal planSemanal = planSemanalRepository
                .findByOrsIdentifkeyPspl(planSemanalKey)
                .orElse(null);

        if (planSemanal == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el plan semanal con el código " + planSemanalKey)
                    .withCode("404")
                    .buildBusinessException();
        }

        EntyOrsplnmaplantrabajo plan = planTrabajoRepository
                .findByOrsIdentifkeyPltr(planSemanal.getOrsIdentifkeyPltr())
                .orElse(null);

        if (plan == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el plan de trabajo asociado al plan semanal "
                            + planSemanalKey)
                    .withCode("404")
                    .buildBusinessException();
        }

        AvanceObraDto avance = calcularAvancePlanSemanal(plan, planSemanal);

        List<AvanceObraDto> avances = new ArrayList<>();
        avances.add(avance);

        AvanceObraResponse response = new AvanceObraResponse();
        response.setRspMessage("OK");
        response.setRspValue("OK");
        response.setRspParentKey(planSemanalKey);
        response.setRspAppKey("msvc-control-obras");
        response.setRspData(avances);

        return response;
    }

    private AvanceObraDto calcularAvancePlan(
            EntyOrsplnmaplantrabajo plan
    ) {
        BigDecimal planeadoTotal = nvl(plan.getOrsCantidadprogPltr());
        BigDecimal ejecutadoTotal = nvl(
                reporteDiarioRepository.sumEjecutadoByPlan(plan.getOrsIdentifkeyPltr())
        );

        BigDecimal saldoTotal = planeadoTotal.subtract(ejecutadoTotal);
        BigDecimal porcentajeTotal = calcularPorcentaje(ejecutadoTotal, planeadoTotal);

        AvanceObraDto dto = new AvanceObraDto();
        dto.setOrsIdentifkeyOrde(plan.getOrsIdentifkeyOrde());
        dto.setOrsIdentifkeyPltr(plan.getOrsIdentifkeyPltr());
        dto.setOrsIdentifkeyPspl(null);
        dto.setDescripcionPlan(plan.getOrsActividadPltr());
        dto.setCantidadPlaneadaTotal(planeadoTotal);
        dto.setCantidadPlaneadaSemana(BigDecimal.ZERO);
        dto.setCantidadEjecutadaTotal(ejecutadoTotal);
        dto.setCantidadEjecutadaSemana(BigDecimal.ZERO);
        dto.setSaldoPendienteTotal(saldoTotal);
        dto.setSaldoPendienteSemana(BigDecimal.ZERO);
        dto.setPorcentajeAvanceTotal(porcentajeTotal);
        dto.setPorcentajeAvanceSemana(BigDecimal.ZERO);
        dto.setUnidadMedida(plan.getOrsUnidadmedidaPltr());
        dto.setEstadoAvance(calcularEstadoAvance(porcentajeTotal));

        return dto;
    }

    private AvanceObraDto calcularAvancePlanSemanal(
            EntyOrsplnmaplantrabajo plan,
            EntyOrspspmdplansemanal planSemanal
    ) {
        BigDecimal planeadoTotal = nvl(plan.getOrsCantidadprogPltr());
        BigDecimal planeadoSemana = nvl(planSemanal.getOrsCantidadprogPspl());

        BigDecimal ejecutadoTotal = nvl(
                reporteDiarioRepository.sumEjecutadoByPlan(plan.getOrsIdentifkeyPltr())
        );

        BigDecimal ejecutadoSemana = nvl(
                reporteDiarioRepository.sumEjecutadoByPlanSemanal(planSemanal.getOrsIdentifkeyPspl())
        );

        BigDecimal saldoTotal = planeadoTotal.subtract(ejecutadoTotal);
        BigDecimal saldoSemana = planeadoSemana.subtract(ejecutadoSemana);

        BigDecimal porcentajeTotal = calcularPorcentaje(ejecutadoTotal, planeadoTotal);
        BigDecimal porcentajeSemana = calcularPorcentaje(ejecutadoSemana, planeadoSemana);

        AvanceObraDto dto = new AvanceObraDto();
        dto.setOrsIdentifkeyOrde(plan.getOrsIdentifkeyOrde());
        dto.setOrsIdentifkeyPltr(plan.getOrsIdentifkeyPltr());
        dto.setOrsIdentifkeyPspl(planSemanal.getOrsIdentifkeyPspl());
        dto.setDescripcionPlan(plan.getOrsActividadPltr());
        dto.setCantidadPlaneadaTotal(planeadoTotal);
        dto.setCantidadPlaneadaSemana(planeadoSemana);
        dto.setCantidadEjecutadaTotal(ejecutadoTotal);
        dto.setCantidadEjecutadaSemana(ejecutadoSemana);
        dto.setSaldoPendienteTotal(saldoTotal);
        dto.setSaldoPendienteSemana(saldoSemana);
        dto.setPorcentajeAvanceTotal(porcentajeTotal);
        dto.setPorcentajeAvanceSemana(porcentajeSemana);
        dto.setUnidadMedida(plan.getOrsUnidadmedidaPltr());
        dto.setEstadoAvance(calcularEstadoAvance(porcentajeTotal));

        return dto;
    }

    private BigDecimal calcularPorcentaje(
            BigDecimal ejecutado,
            BigDecimal planeado
    ) {
        if (planeado == null || BigDecimal.ZERO.compareTo(planeado) == 0) {
            return BigDecimal.ZERO;
        }

        return ejecutado
                .multiply(BigDecimal.valueOf(100))
                .divide(planeado, 2, RoundingMode.HALF_UP);
    }

    private String calcularEstadoAvance(
            BigDecimal porcentaje
    ) {
        if (porcentaje == null) {
            return "SIN_AVANCE";
        }

        if (porcentaje.compareTo(BigDecimal.ZERO) <= 0) {
            return "SIN_AVANCE";
        }

        if (porcentaje.compareTo(BigDecimal.valueOf(50)) < 0) {
            return "EN_PROCESO";
        }

        if (porcentaje.compareTo(BigDecimal.valueOf(100)) < 0) {
            return "AVANCE_ALTO";
        }

        return "COMPLETADO";
    }

    private BigDecimal nvl(
            BigDecimal value
    ) {
        return value == null ? BigDecimal.ZERO : value;
    }
}