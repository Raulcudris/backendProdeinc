package com.system.modules.documentosobra.usecase;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import com.system.crosscutting.domain.model.EntyDocdocmadocumentoDto;
import com.system.crosscutting.domain.model.EntyDocdocmadocumentoResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyDocdocmadocumentoRepository;
import com.system.crosscutting.persistence.repository.EntyDoctipmatipodocumentoRepository;
import com.system.modules.documentosobra.dataproviders.jpa.JpaDocumentoObraDataProviders;
import com.system.modules.documentosobra.services.UseCase;
import com.system.modules.documentosobra.services.UsecaseServices;

@UseCase
public class EntyDocumentoObraService
        extends UsecaseServices<EntyDocdocmadocumentoDto, JpaDocumentoObraDataProviders> {

    @Autowired
    private JpaDocumentoObraDataProviders jpaDataProviders;

    @Autowired
    private EntyDocdocmadocumentoRepository repository;

    @Autowired
    private EntyDoctipmatipodocumentoRepository tipoDocumentoRepository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyDocdocmadocumentoResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyDocdocmadocumentoResponse getByReferencia(
            int currentPage,
            int pageSize,
            String tipoReferencia,
            String referenciaId
    ) throws EBusinessException {

        if (tipoReferencia == null || tipoReferencia.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de referencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (referenciaId == null || referenciaId.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El identificador de referencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        return this.jpaDataProviders.getByReferencia(
                currentPage,
                pageSize,
                tipoReferencia,
                referenciaId
        );
    }

    public EntyDocdocmadocumentoDto saveBefore(
            EntyDocdocmadocumentoDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El documento de obra es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocIdentifkeyDocu() == null || dto.getDocIdentifkeyDocu().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional del documento es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocIdentifkeyTido() == null || dto.getDocIdentifkeyTido().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de documento es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocNombreDocu() == null || dto.getDocNombreDocu().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El nombre del documento es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocUrlarchivoDocu() == null || dto.getDocUrlarchivoDocu().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La URL o ruta del archivo documental es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByDocIdentifkeyDocu(dto.getDocIdentifkeyDocu()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe un documento de obra con el código "
                            + dto.getDocIdentifkeyDocu())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (tipoDocumentoRepository.findByDocIdentifkeyTido(dto.getDocIdentifkeyTido()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el tipo de documento con el código "
                            + dto.getDocIdentifkeyTido())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getDocFechavenceDocu() != null
                && dto.getDocFechaexpDocu() != null
                && dto.getDocFechavenceDocu().isBefore(dto.getDocFechaexpDocu())) {
            throw ExceptionBuilder.builder()
                    .withMessage("La fecha de vencimiento no puede ser menor que la fecha de expedición")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocEstadoregDocu() == null || dto.getDocEstadoregDocu().isBlank()) {
            dto.setDocEstadoregDocu("1");
        }

        return this.jpaDataProviders.save(dto);
    }

    public EntyDocdocmadocumentoDto updateBefore(
            Integer id,
            EntyDocdocmadocumentoDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del documento de obra es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información del documento de obra es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocIdentifkeyTido() == null || dto.getDocIdentifkeyTido().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de documento es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (tipoDocumentoRepository.findByDocIdentifkeyTido(dto.getDocIdentifkeyTido()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el tipo de documento con el código "
                            + dto.getDocIdentifkeyTido())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getDocNombreDocu() == null || dto.getDocNombreDocu().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El nombre del documento es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocUrlarchivoDocu() == null || dto.getDocUrlarchivoDocu().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La URL o ruta del archivo documental es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocFechavenceDocu() != null
                && dto.getDocFechaexpDocu() != null
                && dto.getDocFechavenceDocu().isBefore(dto.getDocFechaexpDocu())) {
            throw ExceptionBuilder.builder()
                    .withMessage("La fecha de vencimiento no puede ser menor que la fecha de expedición")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocEstadoregDocu() == null || dto.getDocEstadoregDocu().isBlank()) {
            dto.setDocEstadoregDocu("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del documento de obra es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyDocdocmadocumentoDto documento = this.jpaDataProviders.get(id);

        if (documento.getDocPrimarykeyDocu() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El documento de obra no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        documento.setDocEstadoregDocu(nextStatus);

        this.jpaDataProviders.update(id, documento);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del documento de obra es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyDocdocmadocumentoDto documento = this.jpaDataProviders.get(id);

        if (documento.getDocPrimarykeyDocu() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El documento de obra no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        documento.setDocEstadoregDocu("2");

        this.jpaDataProviders.update(id, documento);

        return "OK";
    }
}