package com.system.modules.evidencia.usecase;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyEvitipmatipoevidenciaRepository;
import com.system.modules.evidencia.dataproviders.jpa.JpaTipoEvidenciaDataProviders;
import com.system.modules.evidencia.services.UseCase;
import com.system.modules.evidencia.services.UsecaseServices;

@UseCase
public class EntyTipoEvidenciaService
        extends UsecaseServices<EntyEvitipmatipoevidenciaDto, JpaTipoEvidenciaDataProviders> {

    @Autowired
    private JpaTipoEvidenciaDataProviders jpaDataProviders;

    @Autowired
    private EntyEvitipmatipoevidenciaRepository repository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyEvitipmatipoevidenciaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEvitipmatipoevidenciaDto saveBefore(
            EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviIdentifkeyTiev() == null || dto.getEviIdentifkeyTiev().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional del tipo de evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviDescripcionTiev() == null || dto.getEviDescripcionTiev().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La descripción del tipo de evidencia es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByEviIdentifkeyTiev(dto.getEviIdentifkeyTiev()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe un tipo de evidencia con el código "
                            + dto.getEviIdentifkeyTiev())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (dto.getEviEstadoregTiev() == null || dto.getEviEstadoregTiev().isBlank()) {
            dto.setEviEstadoregTiev("1");
        }

        return this.jpaDataProviders.save(dto);
    }

    public EntyEvitipmatipoevidenciaDto updateBefore(
            Integer id,
            EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del tipo de evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información del tipo de evidencia es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviDescripcionTiev() == null || dto.getEviDescripcionTiev().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La descripción del tipo de evidencia es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviEstadoregTiev() == null || dto.getEviEstadoregTiev().isBlank()) {
            dto.setEviEstadoregTiev("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del tipo de evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEvitipmatipoevidenciaDto tipo = this.jpaDataProviders.get(id);

        if (tipo.getEviPrimarykeyTiev() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de evidencia no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        tipo.setEviEstadoregTiev(nextStatus);

        this.jpaDataProviders.update(id, tipo);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del tipo de evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEvitipmatipoevidenciaDto tipo = this.jpaDataProviders.get(id);

        if (tipo.getEviPrimarykeyTiev() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de evidencia no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        tipo.setEviEstadoregTiev("2");

        this.jpaDataProviders.update(id, tipo);

        return "OK";
    }
}