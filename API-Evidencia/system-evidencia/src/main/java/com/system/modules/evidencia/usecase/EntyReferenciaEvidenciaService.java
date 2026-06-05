package com.system.modules.evidencia.usecase;
import javax.annotation.PostConstruct;

import com.system.modules.evidencia.dataproviders.jpa.JpaReferenciaEvidenciaDataProviders;
import com.system.modules.evidencia.services.UseCase;
import com.system.modules.evidencia.services.UsecaseServices;
import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyEvievimaevidenciaRepository;
import com.system.crosscutting.persistence.repository.EntyEvirefmdreferenciaRepository;

@UseCase
public class EntyReferenciaEvidenciaService extends UsecaseServices<EntyEvirefmdreferenciaDto, JpaReferenciaEvidenciaDataProviders> {

    @Autowired
    private JpaReferenciaEvidenciaDataProviders jpaDataProviders;

    @Autowired
    private EntyEvirefmdreferenciaRepository repository;

    @Autowired
    private EntyEvievimaevidenciaRepository evidenciaRepository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyEvirefmdreferenciaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEvirefmdreferenciaResponse getByEvidencia(
            int currentPage,
            int pageSize,
            String evidenciaKey
    ) throws EBusinessException {

        if (evidenciaKey == null || evidenciaKey.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código de la evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        return this.jpaDataProviders.getByEvidencia(currentPage, pageSize, evidenciaKey);
    }

    public EntyEvirefmdreferenciaResponse getByReferencia(
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

    public EntyEvirefmdreferenciaDto saveBefore(
            EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La referencia de evidencia es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviIdentifkeyEvre() == null || dto.getEviIdentifkeyEvre().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional de la referencia de evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviIdentifkeyEvid() == null || dto.getEviIdentifkeyEvid().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código de la evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviTiporeferenEvre() == null || dto.getEviTiporeferenEvre().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de referencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviReferenciaidEvre() == null || dto.getEviReferenciaidEvre().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El identificador del registro referenciado es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByEviIdentifkeyEvre(dto.getEviIdentifkeyEvre()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe una referencia de evidencia con el código "
                            + dto.getEviIdentifkeyEvre())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (evidenciaRepository.findByEviIdentifkeyEvid(dto.getEviIdentifkeyEvid()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe la evidencia con el código "
                            + dto.getEviIdentifkeyEvid())
                    .withCode("404")
                    .buildBusinessException();
        }

        validarTipoReferencia(dto.getEviTiporeferenEvre());

        if (dto.getEviEstadoregEvre() == null || dto.getEviEstadoregEvre().isBlank()) {
            dto.setEviEstadoregEvre("1");
        }

        return this.jpaDataProviders.save(dto);
    }

    public EntyEvirefmdreferenciaDto updateBefore(
            Integer id,
            EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la referencia de evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información de la referencia de evidencia es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviIdentifkeyEvid() == null || dto.getEviIdentifkeyEvid().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código de la evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (evidenciaRepository.findByEviIdentifkeyEvid(dto.getEviIdentifkeyEvid()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe la evidencia con el código "
                            + dto.getEviIdentifkeyEvid())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getEviTiporeferenEvre() == null || dto.getEviTiporeferenEvre().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de referencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviReferenciaidEvre() == null || dto.getEviReferenciaidEvre().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El identificador del registro referenciado es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        validarTipoReferencia(dto.getEviTiporeferenEvre());

        if (dto.getEviEstadoregEvre() == null || dto.getEviEstadoregEvre().isBlank()) {
            dto.setEviEstadoregEvre("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la referencia de evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEvirefmdreferenciaDto referencia = this.jpaDataProviders.get(id);

        if (referencia.getEviPrimarykeyEvre() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La referencia de evidencia no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        referencia.setEviEstadoregEvre(nextStatus);

        this.jpaDataProviders.update(id, referencia);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la referencia de evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEvirefmdreferenciaDto referencia = this.jpaDataProviders.get(id);

        if (referencia.getEviPrimarykeyEvre() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La referencia de evidencia no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        referencia.setEviEstadoregEvre("2");

        this.jpaDataProviders.update(id, referencia);

        return "OK";
    }

    private void validarTipoReferencia(String tipoReferencia) throws EBusinessException {
        if ("ORDEN_SERVICIO".equals(tipoReferencia)
                || "PLAN_TRABAJO".equals(tipoReferencia)
                || "REPORTE_DIARIO".equals(tipoReferencia)
                || "NOVEDAD".equals(tipoReferencia)
                || "DOCUMENTO".equals(tipoReferencia)
                || "EQUIPO".equals(tipoReferencia)
                || "SITIO_TRABAJO".equals(tipoReferencia)) {
            return;
        }

        throw ExceptionBuilder.builder()
                .withMessage("El tipo de referencia debe ser ORDEN_SERVICIO, PLAN_TRABAJO, REPORTE_DIARIO, NOVEDAD, DOCUMENTO, EQUIPO o SITIO_TRABAJO")
                .withCode("400")
                .buildBusinessException();
    }
}