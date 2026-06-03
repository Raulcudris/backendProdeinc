package com.system.modules.documentosobra.usecase;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import com.system.crosscutting.domain.model.EntyDoccatmacategoriaDto;
import com.system.crosscutting.domain.model.EntyDoccatmacategoriaResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyDoccatmacategoriaRepository;
import com.system.modules.documentosobra.dataproviders.jpa.JpaCategoriaDocumentoDataProviders;
import com.system.modules.documentosobra.services.UseCase;
import com.system.modules.documentosobra.services.UsecaseServices;

@UseCase
public class EntyCategoriaDocumentoService
        extends UsecaseServices<EntyDoccatmacategoriaDto, JpaCategoriaDocumentoDataProviders> {

    @Autowired
    private JpaCategoriaDocumentoDataProviders jpaDataProviders;

    @Autowired
    private EntyDoccatmacategoriaRepository repository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyDoccatmacategoriaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.ijpaDataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyDoccatmacategoriaDto saveBefore(
            EntyDoccatmacategoriaDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La categoría documental es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocIdentifkeyCado() == null || dto.getDocIdentifkeyCado().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional de la categoría documental es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocDescripcionCado() == null || dto.getDocDescripcionCado().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La descripción de la categoría documental es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByDocIdentifkeyCado(dto.getDocIdentifkeyCado()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe una categoría documental con el código "
                            + dto.getDocIdentifkeyCado())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (dto.getDocEstadoregCado() == null || dto.getDocEstadoregCado().isBlank()) {
            dto.setDocEstadoregCado("1");
        }

        return this.ijpaDataProvider.save(dto);
    }

    public EntyDoccatmacategoriaDto updateBefore(
            Integer id,
            EntyDoccatmacategoriaDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la categoría documental es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información de la categoría documental es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocDescripcionCado() == null || dto.getDocDescripcionCado().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("La descripción de la categoría documental es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getDocEstadoregCado() == null || dto.getDocEstadoregCado().isBlank()) {
            dto.setDocEstadoregCado("1");
        }

        return this.ijpaDataProvider.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la categoría documental es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyDoccatmacategoriaDto categoria = this.ijpaDataProvider.get(id);

        if (categoria.getDocPrimarykeyCado() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La categoría documental no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        categoria.setDocEstadoregCado(nextStatus);

        this.ijpaDataProvider.update(id, categoria);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la categoría documental es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyDoccatmacategoriaDto categoria = this.ijpaDataProvider.get(id);

        if (categoria.getDocPrimarykeyCado() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La categoría documental no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        categoria.setDocEstadoregCado("2");

        this.ijpaDataProvider.update(id, categoria);

        return "OK";
    }
}