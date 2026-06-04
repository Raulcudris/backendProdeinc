package com.system.modules.equiposmaquinaria.usecase;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyEqumedmaunidadmedidaDto;
import com.system.crosscutting.domain.model.EntyEqumedmaunidadmedidaResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyEqumedmaunidadmedidaRepository;
import com.system.modules.equiposmaquinaria.dataproviders.jpa.JpaUnidadMedidaDataProviders;
import com.system.modules.equiposmaquinaria.services.UseCase;
import com.system.modules.equiposmaquinaria.services.UsecaseServices;

@UseCase
public class EntyUnidadMedidaService
        extends UsecaseServices<EntyEqumedmaunidadmedidaDto, JpaUnidadMedidaDataProviders> {

    @Autowired
    private JpaUnidadMedidaDataProviders jpaDataProviders;

    @Autowired
    private EntyEqumedmaunidadmedidaRepository repository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyEqumedmaunidadmedidaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEqumedmaunidadmedidaDto saveBefore(
            EntyEqumedmaunidadmedidaDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La unidad de medida es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquIdentifkeyUnme() == null || dto.getEquIdentifkeyUnme().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional de la unidad de medida es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquCodigoUnme() == null || dto.getEquCodigoUnme().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código corto de la unidad de medida es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquDescripcionUnme() == null || dto.getEquDescripcionUnme().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La descripción de la unidad de medida es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByEquIdentifkeyUnme(dto.getEquIdentifkeyUnme()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe una unidad de medida con el código funcional "
                            + dto.getEquIdentifkeyUnme())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (repository.findByEquCodigoUnme(dto.getEquCodigoUnme()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe una unidad de medida con el código corto "
                            + dto.getEquCodigoUnme())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (dto.getEquEstadoregUnme() == null || dto.getEquEstadoregUnme().isBlank()) {
            dto.setEquEstadoregUnme("1");
        }

        return this.jpaDataProviders.save(dto);
    }

    public EntyEqumedmaunidadmedidaDto updateBefore(
            Integer id,
            EntyEqumedmaunidadmedidaDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la unidad de medida es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información de la unidad de medida es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquCodigoUnme() == null || dto.getEquCodigoUnme().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código corto de la unidad de medida es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquDescripcionUnme() == null || dto.getEquDescripcionUnme().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La descripción de la unidad de medida es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquEstadoregUnme() == null || dto.getEquEstadoregUnme().isBlank()) {
            dto.setEquEstadoregUnme("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la unidad de medida es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEqumedmaunidadmedidaDto unidad = this.jpaDataProviders.get(id);

        if (unidad.getEquPrimarykeyUnme() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La unidad de medida no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        unidad.setEquEstadoregUnme(nextStatus);

        this.jpaDataProviders.update(id, unidad);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la unidad de medida es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEqumedmaunidadmedidaDto unidad = this.jpaDataProviders.get(id);

        if (unidad.getEquPrimarykeyUnme() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La unidad de medida no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        unidad.setEquEstadoregUnme("2");

        this.jpaDataProviders.update(id, unidad);

        return "OK";
    }
}