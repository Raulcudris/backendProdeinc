package com.system.modules.controlobras.usecase;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyOrssitmdsitioDto;
import com.system.crosscutting.domain.model.EntyOrssitmdsitioResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import com.system.crosscutting.persistence.repository.EntyOrssitmdsitioRepository;
import com.system.modules.controlobras.dataproviders.jpa.JpaSitioDataProviders;
import com.system.modules.controlobras.services.UseCase;
import com.system.modules.controlobras.services.UsecaseServices;

@UseCase
public class SitioService
        extends UsecaseServices<EntyOrssitmdsitioDto, JpaSitioDataProviders> {

    @Autowired
    private JpaSitioDataProviders jpaDataProviders;

    @Autowired
    private EntyOrssitmdsitioRepository repository;

    @Autowired
    private EntyOrsordmaordenservicioRepository ordenRepository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyOrssitmdsitioResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrssitmdsitioResponse getByOrden(
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

    public EntyOrssitmdsitioDto saveBefore(
            EntyOrssitmdsitioDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El sitio de trabajo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeySitr() == null || dto.getOrsIdentifkeySitr().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional del sitio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyOrde() == null || dto.getOrsIdentifkeyOrde().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código de la orden de servicio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByOrsIdentifkeySitr(dto.getOrsIdentifkeySitr()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe un sitio con el código "
                            + dto.getOrsIdentifkeySitr())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (ordenRepository.findByOrsIdentifkeyOrde(dto.getOrsIdentifkeyOrde()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe la orden de servicio con el código "
                            + dto.getOrsIdentifkeyOrde())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getOrsNombreSitr() == null || dto.getOrsNombreSitr().isBlank()) {
            dto.setOrsNombreSitr(dto.getOrsIdentifkeySitr());
        }

        if (dto.getOrsEstadoregSitr() == null || dto.getOrsEstadoregSitr().isBlank()) {
            dto.setOrsEstadoregSitr("1");
        }

        return this.jpaDataProviders.save(dto);
    }

    public EntyOrssitmdsitioDto updateBefore(
            Integer id,
            EntyOrssitmdsitioDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del sitio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información del sitio es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyOrde() == null || dto.getOrsIdentifkeyOrde().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código de la orden de servicio es obligatorio")
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

        if (dto.getOrsNombreSitr() == null || dto.getOrsNombreSitr().isBlank()) {
            dto.setOrsNombreSitr(dto.getOrsIdentifkeySitr());
        }

        if (dto.getOrsEstadoregSitr() == null || dto.getOrsEstadoregSitr().isBlank()) {
            dto.setOrsEstadoregSitr("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del sitio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrssitmdsitioDto sitio = this.jpaDataProviders.get(id);

        if (sitio.getOrsPrimarykeySitr() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El sitio de trabajo no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        sitio.setOrsEstadoregSitr(nextStatus);

        this.jpaDataProviders.update(id, sitio);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del sitio es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrssitmdsitioDto sitio = this.jpaDataProviders.get(id);

        if (sitio.getOrsPrimarykeySitr() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El sitio de trabajo no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        sitio.setOrsEstadoregSitr("2");

        this.jpaDataProviders.update(id, sitio);

        return "OK";
    }
}