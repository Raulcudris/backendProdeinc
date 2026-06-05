package com.system.modules.controlobras.usecase;

import java.math.BigDecimal;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyOrsplnmaplantrabajoDto;
import com.system.crosscutting.domain.model.EntyOrsplnmaplantrabajoResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.crosscutting.persistence.repository.EntyOrsplnmaplantrabajoRepository;
import com.system.crosscutting.persistence.repository.EntyOrssitmdsitioRepository;
import com.system.modules.controlobras.dataproviders.jpa.JpaPlanTrabajoDataProviders;
import com.system.modules.controlobras.services.UseCase;
import com.system.modules.controlobras.services.UsecaseServices;

@UseCase
public class PlanTrabajoService
        extends UsecaseServices<EntyOrsplnmaplantrabajoDto, JpaPlanTrabajoDataProviders> {

    @Autowired
    private JpaPlanTrabajoDataProviders jpaDataProviders;

    @Autowired
    private EntyOrsplnmaplantrabajoRepository repository;

    @Autowired
    private EntyOrsordmaordenservicioRepository ordenRepository;

    @Autowired
    private EntyOrssitmdsitioRepository sitioRepository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyOrsplnmaplantrabajoResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsplnmaplantrabajoResponse getByOrden(
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

    public EntyOrsplnmaplantrabajoResponse getBySitio(
            int currentPage,
            int pageSize,
            String sitioKey
    ) throws EBusinessException {

        if (sitioKey == null || sitioKey.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del sitio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        return this.jpaDataProviders.getBySitio(currentPage, pageSize, sitioKey);
    }

    public EntyOrsplnmaplantrabajoDto saveBefore(
            EntyOrsplnmaplantrabajoDto dto
    ) throws EBusinessException {

        validarDtoBase(dto, true);

        if (repository.findByOrsIdentifkeyPltr(dto.getOrsIdentifkeyPltr()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe un plan de trabajo con el código "
                            + dto.getOrsIdentifkeyPltr())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (dto.getOrsCantidadprogPltr() == null) {
            dto.setOrsCantidadprogPltr(BigDecimal.ZERO);
        }

        if (dto.getOrsEstadoregPltr() == null || dto.getOrsEstadoregPltr().isBlank()) {
            dto.setOrsEstadoregPltr("1");
        }

        return this.jpaDataProviders.save(dto);
    }

    public EntyOrsplnmaplantrabajoDto updateBefore(
            Integer id,
            EntyOrsplnmaplantrabajoDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del plan de trabajo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        validarDtoBase(dto, false);

        if (dto.getOrsCantidadprogPltr() == null) {
            dto.setOrsCantidadprogPltr(BigDecimal.ZERO);
        }

        if (dto.getOrsEstadoregPltr() == null || dto.getOrsEstadoregPltr().isBlank()) {
            dto.setOrsEstadoregPltr("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del plan de trabajo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrsplnmaplantrabajoDto plan = this.jpaDataProviders.get(id);

        if (plan.getOrsPrimarykeyPltr() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El plan de trabajo no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado) || "3".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        plan.setOrsEstadoregPltr(nextStatus);

        this.jpaDataProviders.update(id, plan);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del plan de trabajo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrsplnmaplantrabajoDto plan = this.jpaDataProviders.get(id);

        if (plan.getOrsPrimarykeyPltr() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El plan de trabajo no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        plan.setOrsEstadoregPltr("2");

        this.jpaDataProviders.update(id, plan);

        return "OK";
    }

    private void validarDtoBase(
            EntyOrsplnmaplantrabajoDto dto,
            boolean validarCodigoDuplicado
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El plan de trabajo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyPltr() == null || dto.getOrsIdentifkeyPltr().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional del plan de trabajo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyOrde() == null || dto.getOrsIdentifkeyOrde().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código de la orden de servicio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeySitr() == null || dto.getOrsIdentifkeySitr().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del sitio de trabajo es obligatorio")
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

        if (sitioRepository.findByOrsIdentifkeySitr(dto.getOrsIdentifkeySitr()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el sitio de trabajo con el código "
                            + dto.getOrsIdentifkeySitr())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getOrsFechainicioPltr() != null
                && dto.getOrsFechafinPltr() != null
                && dto.getOrsFechafinPltr().isBefore(dto.getOrsFechainicioPltr())) {
            throw ExceptionBuilder.builder()
                    .withMessage("La fecha final del plan no puede ser menor que la fecha inicial")
                    .withCode("400")
                    .buildBusinessException();
        }
    }
}