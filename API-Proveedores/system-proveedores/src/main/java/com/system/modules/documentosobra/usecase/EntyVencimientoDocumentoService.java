package com.system.modules.documentosobra.usecase;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyDocvenmdvencimientoDto;
import com.system.crosscutting.domain.model.EntyDocvenmdvencimientoResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyDocdocmadocumentoRepository;
import com.system.crosscutting.persistence.repository.EntyDocvenmdvencimientoRepository;
import com.system.modules.documentosobra.dataproviders.jpa.JpaVencimientoDocumentoDataProviders;
import com.system.modules.documentosobra.services.UseCase;
import com.system.modules.documentosobra.services.UsecaseServices;

@UseCase
public class EntyVencimientoDocumentoService
        extends UsecaseServices<EntyDocvenmdvencimientoDto, JpaVencimientoDocumentoDataProviders> {

    @Autowired
    private JpaVencimientoDocumentoDataProviders jpaDataProviders;

    @Autowired
    private EntyDocvenmdvencimientoRepository repository;

    @Autowired
    private EntyDocdocmadocumentoRepository documentoRepository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyDocvenmdvencimientoResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyDocvenmdvencimientoResponse getByDocumento(
            int currentPage,
            int pageSize,
            String documentoKey
    ) throws EBusinessException {

        if (documentoKey == null || documentoKey.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del documento es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        return this.jpaDataProviders.getByDocumento(currentPage, pageSize, documentoKey);
    }

    public EntyDocvenmdvencimientoResponse getProximos(
            int currentPage,
            int pageSize,
            int dias
    ) throws EBusinessException {
        return this.jpaDataProviders.getProximos(currentPage, pageSize, dias);
    }

    public EntyDocvenmdvencimientoResponse getVencidos(
            int currentPage,
            int pageSize
    ) throws EBusinessException {
        return this.jpaDataProviders.getVencidos(currentPage, pageSize);
    }

    public EntyDocvenmdvencimientoDto saveBefore(
            EntyDocvenmdvencimientoDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El vencimiento documental es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocIdentifkeyVedo() == null || dto.getDocIdentifkeyVedo().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional del vencimiento documental es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocIdentifkeyDocu() == null || dto.getDocIdentifkeyDocu().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del documento asociado es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocFechavenceVedo() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La fecha de vencimiento documental es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByDocIdentifkeyVedo(dto.getDocIdentifkeyVedo()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe un vencimiento documental con el código "
                            + dto.getDocIdentifkeyVedo())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (documentoRepository.findByDocIdentifkeyDocu(dto.getDocIdentifkeyDocu()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el documento asociado con el código "
                            + dto.getDocIdentifkeyDocu())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getDocDiasalertaVedo() == null || dto.getDocDiasalertaVedo() <= 0) {
            dto.setDocDiasalertaVedo(30);
        }

        if (dto.getDocEstadovencVedo() == null || dto.getDocEstadovencVedo().isBlank()) {
            dto.setDocEstadovencVedo("1");
        }

        validarEstadoVencimiento(dto.getDocEstadovencVedo());

        if (dto.getDocEstadoregVedo() == null || dto.getDocEstadoregVedo().isBlank()) {
            dto.setDocEstadoregVedo("1");
        }

        return this.jpaDataProviders.save(dto);
    }

    public EntyDocvenmdvencimientoDto updateBefore(
            Integer id,
            EntyDocvenmdvencimientoDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del vencimiento documental es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información del vencimiento documental es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocIdentifkeyDocu() == null || dto.getDocIdentifkeyDocu().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del documento asociado es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (documentoRepository.findByDocIdentifkeyDocu(dto.getDocIdentifkeyDocu()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el documento asociado con el código "
                            + dto.getDocIdentifkeyDocu())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getDocFechavenceVedo() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La fecha de vencimiento documental es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocDiasalertaVedo() == null || dto.getDocDiasalertaVedo() <= 0) {
            dto.setDocDiasalertaVedo(30);
        }

        if (dto.getDocEstadovencVedo() == null || dto.getDocEstadovencVedo().isBlank()) {
            dto.setDocEstadovencVedo("1");
        }

        validarEstadoVencimiento(dto.getDocEstadovencVedo());

        if ("4".equals(dto.getDocEstadovencVedo()) && dto.getDocFecharenovaVedo() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La fecha de renovación es obligatoria cuando el estado es renovado")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocEstadoregVedo() == null || dto.getDocEstadoregVedo().isBlank()) {
            dto.setDocEstadoregVedo("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del vencimiento documental es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        validarEstadoVencimiento(estado);

        EntyDocvenmdvencimientoDto vencimiento = this.jpaDataProviders.get(id);

        if (vencimiento.getDocPrimarykeyVedo() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El vencimiento documental no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        vencimiento.setDocEstadovencVedo(estado);

        this.jpaDataProviders.update(id, vencimiento);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del vencimiento documental es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyDocvenmdvencimientoDto vencimiento = this.jpaDataProviders.get(id);

        if (vencimiento.getDocPrimarykeyVedo() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El vencimiento documental no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        vencimiento.setDocEstadoregVedo("2");

        this.jpaDataProviders.update(id, vencimiento);

        return "OK";
    }

    private void validarEstadoVencimiento(String estado) throws EBusinessException {
        if (!"1".equals(estado)
                && !"2".equals(estado)
                && !"3".equals(estado)
                && !"4".equals(estado)) {
            throw ExceptionBuilder.builder()
                    .withMessage("El estado de vencimiento debe ser 1=Vigente, 2=Próximo, 3=Vencido o 4=Renovado")
                    .withCode("400")
                    .buildBusinessException();
        }
    }
}