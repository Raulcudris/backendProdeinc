package com.system.modules.evidencia.usecase;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import com.system.modules.evidencia.dataproviders.jpa.JpaEvidenciaDataProviders;
import com.system.modules.evidencia.services.UseCase;
import com.system.modules.evidencia.services.UsecaseServices;
import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyEvievimaevidenciaRepository;
import com.system.crosscutting.persistence.repository.EntyEvitipmatipoevidenciaRepository;

@UseCase
public class EntyEvidenciaService
        extends UsecaseServices<EntyEvievimaevidenciaDto, JpaEvidenciaDataProviders> {

    @Autowired
    private JpaEvidenciaDataProviders jpaDataProviders;

    @Autowired
    private EntyEvievimaevidenciaRepository repository;

    @Autowired
    private EntyEvitipmatipoevidenciaRepository tipoEvidenciaRepository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyEvievimaevidenciaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEvievimaevidenciaDto saveBefore(
            EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La evidencia es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviIdentifkeyEvid() == null || dto.getEviIdentifkeyEvid().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional de la evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviIdentifkeyTiev() == null || dto.getEviIdentifkeyTiev().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviNombreEvid() == null || dto.getEviNombreEvid().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El nombre de la evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviUrlarchivoEvid() == null || dto.getEviUrlarchivoEvid().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La URL o ruta del archivo de evidencia es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByEviIdentifkeyEvid(dto.getEviIdentifkeyEvid()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe una evidencia con el código "
                            + dto.getEviIdentifkeyEvid())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (tipoEvidenciaRepository.findByEviIdentifkeyTiev(dto.getEviIdentifkeyTiev()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el tipo de evidencia con el código "
                            + dto.getEviIdentifkeyTiev())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getEviFechacapturaEvid() == null) {
            dto.setEviFechacapturaEvid(LocalDateTime.now());
        }

        if (dto.getEviEstadoregEvid() == null || dto.getEviEstadoregEvid().isBlank()) {
            dto.setEviEstadoregEvid("1");
        }

        return this.jpaDataProviders.save(dto);
    }

    public EntyEvievimaevidenciaDto updateBefore(
            Integer id,
            EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información de la evidencia es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviIdentifkeyTiev() == null || dto.getEviIdentifkeyTiev().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (tipoEvidenciaRepository.findByEviIdentifkeyTiev(dto.getEviIdentifkeyTiev()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el tipo de evidencia con el código "
                            + dto.getEviIdentifkeyTiev())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getEviNombreEvid() == null || dto.getEviNombreEvid().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El nombre de la evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviUrlarchivoEvid() == null || dto.getEviUrlarchivoEvid().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La URL o ruta del archivo de evidencia es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEviFechacapturaEvid() == null) {
            dto.setEviFechacapturaEvid(LocalDateTime.now());
        }

        if (dto.getEviEstadoregEvid() == null || dto.getEviEstadoregEvid().isBlank()) {
            dto.setEviEstadoregEvid("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEvievimaevidenciaDto evidencia = this.jpaDataProviders.get(id);

        if (evidencia.getEviPrimarykeyEvid() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La evidencia no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        evidencia.setEviEstadoregEvid(nextStatus);

        this.jpaDataProviders.update(id, evidencia);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la evidencia es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEvievimaevidenciaDto evidencia = this.jpaDataProviders.get(id);

        if (evidencia.getEviPrimarykeyEvid() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La evidencia no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        evidencia.setEviEstadoregEvid("2");

        this.jpaDataProviders.update(id, evidencia);

        return "OK";
    }
}