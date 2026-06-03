package com.system.modules.controlobras.usecase;

import javax.annotation.PostConstruct;

import com.system.modules.controlobras.services.UseCase;
import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.modules.controlobras.dataproviders.jpa.JpaOrdenServicioDataProviders;
import com.system.modules.controlobras.services.UsecaseServices;

@UseCase
public class OrdenServicioService
        extends UsecaseServices<EntyOrsordmaordenservicioDto, JpaOrdenServicioDataProviders> {

    @Autowired
    private JpaOrdenServicioDataProviders jpaDataProviders;

    @Autowired
    private EntyOrsordmaordenservicioRepository repository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyOrsordmaordenservicioResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.ijpaDataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsordmaordenservicioDto saveBefore(
            EntyOrsordmaordenservicioDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La orden de servicio es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyOrde() == null || dto.getOrsIdentifkeyOrde().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional de la orden de servicio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByOrsIdentifkeyOrde(dto.getOrsIdentifkeyOrde()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe una orden de servicio con el código "
                            + dto.getOrsIdentifkeyOrde())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (dto.getOrsEstadoregOrde() == null || dto.getOrsEstadoregOrde().isBlank()) {
            dto.setOrsEstadoregOrde("1");
        }

        return this.ijpaDataProvider.save(dto);
    }

    public EntyOrsordmaordenservicioDto updateBefore(
            Integer id,
            EntyOrsordmaordenservicioDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la orden de servicio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información de la orden de servicio es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsEstadoregOrde() == null || dto.getOrsEstadoregOrde().isBlank()) {
            dto.setOrsEstadoregOrde("1");
        }

        return this.ijpaDataProvider.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la orden de servicio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrsordmaordenservicioDto orden = this.ijpaDataProvider.get(id);

        if (orden.getOrsPrimarykeyOrde() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La orden de servicio no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado) || "3".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        orden.setOrsEstadoregOrde(nextStatus);

        this.ijpaDataProvider.update(id, orden);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la orden de servicio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrsordmaordenservicioDto orden = this.ijpaDataProvider.get(id);

        if (orden.getOrsPrimarykeyOrde() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La orden de servicio no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        orden.setOrsEstadoregOrde("3");

        this.ijpaDataProvider.update(id, orden);

        return "OK";
    }
}