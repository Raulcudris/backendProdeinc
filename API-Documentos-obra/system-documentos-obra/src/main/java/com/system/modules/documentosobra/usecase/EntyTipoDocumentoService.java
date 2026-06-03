package com.system.modules.documentosobra.usecase;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyDoctipmatipodocumentoDto;
import com.system.crosscutting.domain.model.EntyDoctipmatipodocumentoResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyDoccatmacategoriaRepository;
import com.system.crosscutting.persistence.repository.EntyDoctipmatipodocumentoRepository;
import com.system.modules.documentosobra.dataproviders.jpa.JpaTipoDocumentoDataProviders;
import com.system.modules.documentosobra.services.UseCase;
import com.system.modules.documentosobra.services.UsecaseServices;

@UseCase
public class EntyTipoDocumentoService
        extends UsecaseServices<EntyDoctipmatipodocumentoDto, JpaTipoDocumentoDataProviders> {

    @Autowired
    private JpaTipoDocumentoDataProviders jpaDataProviders;

    @Autowired
    private EntyDoctipmatipodocumentoRepository repository;

    @Autowired
    private EntyDoccatmacategoriaRepository categoriaRepository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyDoctipmatipodocumentoResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.ijpaDataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyDoctipmatipodocumentoDto saveBefore(
            EntyDoctipmatipodocumentoDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de documento es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocIdentifkeyTido() == null || dto.getDocIdentifkeyTido().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional del tipo de documento es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocIdentifkeyCado() == null || dto.getDocIdentifkeyCado().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código de la categoría documental es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocDescripcionTido() == null || dto.getDocDescripcionTido().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La descripción del tipo de documento es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByDocIdentifkeyTido(dto.getDocIdentifkeyTido()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe un tipo de documento con el código "
                            + dto.getDocIdentifkeyTido())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (categoriaRepository.findByDocIdentifkeyCado(dto.getDocIdentifkeyCado()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe la categoría documental con el código "
                            + dto.getDocIdentifkeyCado())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getDocRequievenceTido() == null || dto.getDocRequievenceTido().isBlank()) {
            dto.setDocRequievenceTido("2");
        }

        if (!"1".equals(dto.getDocRequievenceTido()) && !"2".equals(dto.getDocRequievenceTido())) {
            throw ExceptionBuilder.builder()
                    .withMessage("El indicador de vencimiento debe ser 1=Sí o 2=No")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocEstadoregTido() == null || dto.getDocEstadoregTido().isBlank()) {
            dto.setDocEstadoregTido("1");
        }

        return this.ijpaDataProvidersSave(dto);
    }

    private EntyDoctipmatipodocumentoDto ijpaDataProvidersSave(
            EntyDoctipmatipodocumentoDto dto
    ) throws EBusinessException {
        return this.jpaDataProviders.save(dto);
    }

    public EntyDoctipmatipodocumentoDto updateBefore(
            Integer id,
            EntyDoctipmatipodocumentoDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del tipo de documento es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información del tipo de documento es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocIdentifkeyCado() == null || dto.getDocIdentifkeyCado().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código de la categoría documental es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (categoriaRepository.findByDocIdentifkeyCado(dto.getDocIdentifkeyCado()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe la categoría documental con el código "
                            + dto.getDocIdentifkeyCado())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getDocDescripcionTido() == null || dto.getDocDescripcionTido().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La descripción del tipo de documento es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocRequievenceTido() == null || dto.getDocRequievenceTido().isBlank()) {
            dto.setDocRequievenceTido("2");
        }

        if (!"1".equals(dto.getDocRequievenceTido()) && !"2".equals(dto.getDocRequievenceTido())) {
            throw ExceptionBuilder.builder()
                    .withMessage("El indicador de vencimiento debe ser 1=Sí o 2=No")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocEstadoregTido() == null || dto.getDocEstadoregTido().isBlank()) {
            dto.setDocEstadoregTido("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del tipo de documento es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyDoctipmatipodocumentoDto tipo = this.jpaDataProviders.get(id);

        if (tipo.getDocPrimarykeyTido() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de documento no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        tipo.setDocEstadoregTido(nextStatus);

        this.jpaDataProviders.update(id, tipo);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del tipo de documento es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyDoctipmatipodocumentoDto tipo = this.jpaDataProviders.get(id);

        if (tipo.getDocPrimarykeyTido() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de documento no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        tipo.setDocEstadoregTido("2");

        this.jpaDataProviders.update(id, tipo);

        return "OK";
    }
}