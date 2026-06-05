package com.system.modules.controlobras.usecase;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyOrspspmdplansemanalDto;
import com.system.crosscutting.domain.model.EntyOrspspmdplansemanalResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyOrsplnmaplantrabajoRepository;
import com.system.crosscutting.persistence.repository.EntyOrspspmdplansemanalRepository;
import com.system.modules.controlobras.dataproviders.jpa.JpaPlanSemanalDataProviders;
import com.system.modules.controlobras.services.UseCase;
import com.system.modules.controlobras.services.UsecaseServices;

@UseCase
public class PlanSemanalService
        extends UsecaseServices<EntyOrspspmdplansemanalDto, JpaPlanSemanalDataProviders> {

    @Autowired
    private JpaPlanSemanalDataProviders jpaDataProviders;

    @Autowired
    private EntyOrspspmdplansemanalRepository repository;

    @Autowired
    private EntyOrsplnmaplantrabajoRepository planTrabajoRepository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyOrspspmdplansemanalResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrspspmdplansemanalResponse getByPlan(
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

    public EntyOrspspmdplansemanalDto saveBefore(
            EntyOrspspmdplansemanalDto dto
    ) throws EBusinessException {

        validarDtoBase(dto);

        if (repository.findByOrsIdentifkeyPspl(dto.getOrsIdentifkeyPspl()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe una proyección semanal con el código "
                            + dto.getOrsIdentifkeyPspl())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (dto.getOrsCantidadprogPspl() == null) {
            dto.setOrsCantidadprogPspl(BigDecimal.ZERO);
        }

        if (dto.getOrsEstadoregPspl() == null || dto.getOrsEstadoregPspl().isBlank()) {
            dto.setOrsEstadoregPspl("1");
        }

        return this.jpaDataProviders.save(dto);
    }

    public EntyOrspspmdplansemanalDto updateBefore(
            Integer id,
            EntyOrspspmdplansemanalDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la proyección semanal es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        validarDtoBase(dto);

        if (dto.getOrsCantidadprogPspl() == null) {
            dto.setOrsCantidadprogPspl(BigDecimal.ZERO);
        }

        if (dto.getOrsEstadoregPspl() == null || dto.getOrsEstadoregPspl().isBlank()) {
            dto.setOrsEstadoregPspl("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la proyección semanal es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrspspmdplansemanalDto planSemanal = this.jpaDataProviders.get(id);

        if (planSemanal.getOrsPrimarykeyPspl() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La proyección semanal no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado) || "3".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        planSemanal.setOrsEstadoregPspl(nextStatus);

        this.jpaDataProviders.update(id, planSemanal);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la proyección semanal es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrspspmdplansemanalDto planSemanal = this.jpaDataProviders.get(id);

        if (planSemanal.getOrsPrimarykeyPspl() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La proyección semanal no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        planSemanal.setOrsEstadoregPspl("2");

        this.jpaDataProviders.update(id, planSemanal);

        return "OK";
    }

    private void validarDtoBase(
            EntyOrspspmdplansemanalDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La proyección semanal es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyPspl() == null || dto.getOrsIdentifkeyPspl().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional de la proyección semanal es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyPltr() == null || dto.getOrsIdentifkeyPltr().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del plan de trabajo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (planTrabajoRepository.findByOrsIdentifkeyPltr(dto.getOrsIdentifkeyPltr()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el plan de trabajo con el código "
                            + dto.getOrsIdentifkeyPltr())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getOrsSemanaPspl() == null || dto.getOrsSemanaPspl() <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El número de semana debe ser mayor a cero")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsFechainicioPspl() != null
                && dto.getOrsFechafinPspl() != null
                && dto.getOrsFechafinPspl().isBefore(dto.getOrsFechainicioPspl())) {
            throw ExceptionBuilder.builder()
                    .withMessage("La fecha final de la semana no puede ser menor que la fecha inicial")
                    .withCode("400")
                    .buildBusinessException();
        }
    }
}