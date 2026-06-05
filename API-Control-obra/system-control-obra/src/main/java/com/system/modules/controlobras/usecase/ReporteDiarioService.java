package com.system.modules.controlobras.usecase;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyOrsrdomdreporteDiarioDto;
import com.system.crosscutting.domain.model.EntyOrsrdomdreporteDiarioResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.crosscutting.persistence.repository.EntyOrsplnmaplantrabajoRepository;
import com.system.crosscutting.persistence.repository.EntyOrspspmdplansemanalRepository;
import com.system.crosscutting.persistence.repository.EntyOrsrdomdreporteDiarioRepository;
import com.system.modules.controlobras.dataproviders.jpa.JpaReporteDiarioDataProviders;
import com.system.modules.controlobras.services.UseCase;
import com.system.modules.controlobras.services.UsecaseServices;

@UseCase
public class ReporteDiarioService
        extends UsecaseServices<EntyOrsrdomdreporteDiarioDto, JpaReporteDiarioDataProviders> {

    @Autowired
    private JpaReporteDiarioDataProviders jpaDataProviders;

    @Autowired
    private EntyOrsrdomdreporteDiarioRepository repository;

    @Autowired
    private EntyOrsordmaordenservicioRepository ordenRepository;

    @Autowired
    private EntyOrsplnmaplantrabajoRepository planTrabajoRepository;

    @Autowired
    private EntyOrspspmdplansemanalRepository planSemanalRepository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyOrsrdomdreporteDiarioResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsrdomdreporteDiarioResponse getByOrden(
            int currentPage,
            int pageSize,
            String ordenKey
    ) throws EBusinessException {

        if (ordenKey == null || ordenKey.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código de la orden de servicio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        return this.jpaDataProviders.getByOrden(currentPage, pageSize, ordenKey);
    }

    public EntyOrsrdomdreporteDiarioResponse getByPlan(
            int currentPage,
            int pageSize,
            String planKey
    ) throws EBusinessException {

        if (planKey == null || planKey.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del plan de trabajo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        return this.jpaDataProviders.getByPlan(currentPage, pageSize, planKey);
    }

    public EntyOrsrdomdreporteDiarioResponse getByPlanSemanal(
            int currentPage,
            int pageSize,
            String planSemanalKey
    ) throws EBusinessException {

        if (planSemanalKey == null || planSemanalKey.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del plan semanal es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        return this.jpaDataProviders.getByPlanSemanal(currentPage, pageSize, planSemanalKey);
    }

    public EntyOrsrdomdreporteDiarioDto saveBefore(
            EntyOrsrdomdreporteDiarioDto dto
    ) throws EBusinessException {

        validarDtoBase(dto);

        if (repository.findByOrsIdentifkeyRedi(dto.getOrsIdentifkeyRedi()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe un reporte diario con el código "
                            + dto.getOrsIdentifkeyRedi())
                    .withCode("409")
                    .buildBusinessException();
        }

        normalizarValores(dto);

        return this.jpaDataProviders.save(dto);
    }

    public EntyOrsrdomdreporteDiarioDto updateBefore(
            Integer id,
            EntyOrsrdomdreporteDiarioDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del reporte diario es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        validarDtoBase(dto);

        normalizarValores(dto);

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del reporte diario es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrsrdomdreporteDiarioDto reporte = this.jpaDataProviders.get(id);

        if (reporte.getOrsPrimarykeyRedi() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El reporte diario no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado) || "3".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        reporte.setOrsEstadoregRedi(nextStatus);

        this.jpaDataProviders.update(id, reporte);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del reporte diario es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrsrdomdreporteDiarioDto reporte = this.jpaDataProviders.get(id);

        if (reporte.getOrsPrimarykeyRedi() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El reporte diario no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        reporte.setOrsEstadoregRedi("2");

        this.jpaDataProviders.update(id, reporte);

        return "OK";
    }

    private void validarDtoBase(
            EntyOrsrdomdreporteDiarioDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El reporte diario es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyRedi() == null || dto.getOrsIdentifkeyRedi().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional del reporte diario es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyOrde() == null || dto.getOrsIdentifkeyOrde().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código de la orden de servicio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyPltr() == null || dto.getOrsIdentifkeyPltr().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del plan de trabajo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (ordenRepository.findByOrsIdentifkeyOrde(dto.getOrsIdentifkeyOrde()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe la orden de servicio con el código "
                            + dto.getOrsIdentifkeyOrde())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (planTrabajoRepository.findByOrsIdentifkeyPltr(dto.getOrsIdentifkeyPltr()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el plan de trabajo con el código "
                            + dto.getOrsIdentifkeyPltr())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyPspl() != null
                && !dto.getOrsIdentifkeyPspl().isBlank()
                && planSemanalRepository.findByOrsIdentifkeyPspl(dto.getOrsIdentifkeyPspl()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el plan semanal con el código "
                            + dto.getOrsIdentifkeyPspl())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getOrsFechareporteRedi() == null) {
            dto.setOrsFechareporteRedi(LocalDate.now());
        }
    }

    private void normalizarValores(
            EntyOrsrdomdreporteDiarioDto dto
    ) {
        if (dto.getOrsCantidadprogRedi() == null) {
            dto.setOrsCantidadprogRedi(BigDecimal.ZERO);
        }

        if (dto.getOrsCantidadejecRedi() == null) {
            dto.setOrsCantidadejecRedi(BigDecimal.ZERO);
        }

        if (dto.getOrsEstadoregRedi() == null || dto.getOrsEstadoregRedi().isBlank()) {
            dto.setOrsEstadoregRedi("1");
        }
    }
}