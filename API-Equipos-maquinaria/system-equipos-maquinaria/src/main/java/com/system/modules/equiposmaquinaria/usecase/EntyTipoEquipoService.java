package com.system.modules.equiposmaquinaria.usecase;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyEqutipmatipoequipoDto;
import com.system.crosscutting.domain.model.EntyEqutipmatipoequipoResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyEqutipmatipoequipoRepository;
import com.system.modules.equiposmaquinaria.dataproviders.jpa.JpaTipoEquipoDataProviders;
import com.system.modules.equiposmaquinaria.services.UseCase;
import com.system.modules.equiposmaquinaria.services.UsecaseServices;

@UseCase
public class EntyTipoEquipoService
        extends UsecaseServices<EntyEqutipmatipoequipoDto, JpaTipoEquipoDataProviders> {

    @Autowired
    private JpaTipoEquipoDataProviders jpaDataProviders;

    @Autowired
    private EntyEqutipmatipoequipoRepository repository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyEqutipmatipoequipoResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEqutipmatipoequipoDto saveBefore(
            EntyEqutipmatipoequipoDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquIdentifkeyTieq() == null || dto.getEquIdentifkeyTieq().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional del tipo de equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquDescripcionTieq() == null || dto.getEquDescripcionTieq().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La descripción del tipo de equipo es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByEquIdentifkeyTieq(dto.getEquIdentifkeyTieq()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe un tipo de equipo con el código "
                            + dto.getEquIdentifkeyTieq())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (dto.getEquEstadoregTieq() == null || dto.getEquEstadoregTieq().isBlank()) {
            dto.setEquEstadoregTieq("1");
        }

        return this.jpaDataProviders.save(dto);
    }

    public EntyEqutipmatipoequipoDto updateBefore(
            Integer id,
            EntyEqutipmatipoequipoDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del tipo de equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información del tipo de equipo es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquDescripcionTieq() == null || dto.getEquDescripcionTieq().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La descripción del tipo de equipo es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquEstadoregTieq() == null || dto.getEquEstadoregTieq().isBlank()) {
            dto.setEquEstadoregTieq("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del tipo de equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEqutipmatipoequipoDto tipo = this.jpaDataProviders.get(id);

        if (tipo.getEquPrimarykeyTieq() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de equipo no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        tipo.setEquEstadoregTieq(nextStatus);

        this.jpaDataProviders.update(id, tipo);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del tipo de equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEqutipmatipoequipoDto tipo = this.jpaDataProviders.get(id);

        if (tipo.getEquPrimarykeyTieq() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de equipo no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        tipo.setEquEstadoregTieq("2");

        this.jpaDataProviders.update(id, tipo);

        return "OK";
    }
}