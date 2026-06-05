package com.system.modules.controlobras.usecase;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyOrsnovmdnovedadDto;
import com.system.crosscutting.domain.model.EntyOrsnovmdnovedadResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyOrsnovmdnovedadRepository;
import com.system.crosscutting.persistence.repository.EntyOrsrdomdreporteDiarioRepository;
import com.system.modules.controlobras.dataproviders.jpa.JpaNovedadDataProviders;
import com.system.modules.controlobras.services.UseCase;
import com.system.modules.controlobras.services.UsecaseServices;

@UseCase
public class NovedadService
        extends UsecaseServices<EntyOrsnovmdnovedadDto, JpaNovedadDataProviders> {

    @Autowired
    private JpaNovedadDataProviders jpaDataProviders;

    @Autowired
    private EntyOrsnovmdnovedadRepository repository;

    @Autowired
    private EntyOrsrdomdreporteDiarioRepository reporteDiarioRepository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyOrsnovmdnovedadResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsnovmdnovedadResponse getByReporte(
            int currentPage,
            int pageSize,
            String reporteKey
    ) throws EBusinessException {

        if (reporteKey == null || reporteKey.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del reporte diario es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        return this.jpaDataProviders.getByReporte(currentPage, pageSize, reporteKey);
    }

    public EntyOrsnovmdnovedadDto saveBefore(
            EntyOrsnovmdnovedadDto dto
    ) throws EBusinessException {

        validarDtoBase(dto);

        if (repository.findByOrsIdentifkeyNove(dto.getOrsIdentifkeyNove()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe una novedad con el código "
                            + dto.getOrsIdentifkeyNove())
                    .withCode("409")
                    .buildBusinessException();
        }

        normalizarValores(dto);

        return this.jpaDataProviders.save(dto);
    }

    public EntyOrsnovmdnovedadDto updateBefore(
            Integer id,
            EntyOrsnovmdnovedadDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la novedad es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        validarDtoBase(dto);

        normalizarValores(dto);

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la novedad es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrsnovmdnovedadDto novedad = this.jpaDataProviders.get(id);

        if (novedad.getOrsPrimarykeyNove() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La novedad no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado) || "3".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        novedad.setOrsEstadoregNove(nextStatus);

        this.jpaDataProviders.update(id, novedad);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la novedad es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyOrsnovmdnovedadDto novedad = this.jpaDataProviders.get(id);

        if (novedad.getOrsPrimarykeyNove() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La novedad no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        novedad.setOrsEstadoregNove("2");

        this.jpaDataProviders.update(id, novedad);

        return "OK";
    }

    private void validarDtoBase(
            EntyOrsnovmdnovedadDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La novedad es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyNove() == null || dto.getOrsIdentifkeyNove().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional de la novedad es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getOrsIdentifkeyRedi() == null || dto.getOrsIdentifkeyRedi().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del reporte diario es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (reporteDiarioRepository.findByOrsIdentifkeyRedi(dto.getOrsIdentifkeyRedi()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el reporte diario con el código "
                            + dto.getOrsIdentifkeyRedi())
                    .withCode("404")
                    .buildBusinessException();
        }

        validarTipoNovedad(dto.getOrsTiponovedadNove());
        validarCriticidad(dto.getOrsCriticidadNove());
    }

    private void normalizarValores(
            EntyOrsnovmdnovedadDto dto
    ) {
        if (dto.getOrsFechanovedadNove() == null) {
            dto.setOrsFechanovedadNove(LocalDate.now());
        }

        if (dto.getOrsTiponovedadNove() == null || dto.getOrsTiponovedadNove().isBlank()) {
            dto.setOrsTiponovedadNove("OTRO");
        }

        if (dto.getOrsCriticidadNove() == null || dto.getOrsCriticidadNove().isBlank()) {
            dto.setOrsCriticidadNove("BAJA");
        }

        if (dto.getOrsRequiereaccionNove() == null || dto.getOrsRequiereaccionNove().isBlank()) {
            dto.setOrsRequiereaccionNove("0");
        }

        if (dto.getOrsEstadoregNove() == null || dto.getOrsEstadoregNove().isBlank()) {
            dto.setOrsEstadoregNove("1");
        }
    }

    private void validarTipoNovedad(String tipo) throws EBusinessException {
        if (tipo == null || tipo.isBlank()) {
            return;
        }

        if ("CLIMA".equals(tipo)
                || "DAÑO_MAQUINARIA".equals(tipo)
                || "FALTA_MATERIAL".equals(tipo)
                || "ACCESO_RESTRINGIDO".equals(tipo)
                || "OBSERVACION_TECNICA".equals(tipo)
                || "INTERVENTORIA".equals(tipo)
                || "OTRO".equals(tipo)) {
            return;
        }

        throw ExceptionBuilder.builder()
                .withMessage("El tipo de novedad debe ser CLIMA, DAÑO_MAQUINARIA, FALTA_MATERIAL, ACCESO_RESTRINGIDO, OBSERVACION_TECNICA, INTERVENTORIA u OTRO")
                .withCode("400")
                .buildBusinessException();
    }

    private void validarCriticidad(String criticidad) throws EBusinessException {
        if (criticidad == null || criticidad.isBlank()) {
            return;
        }

        if ("BAJA".equals(criticidad)
                || "MEDIA".equals(criticidad)
                || "ALTA".equals(criticidad)
                || "CRITICA".equals(criticidad)) {
            return;
        }

        throw ExceptionBuilder.builder()
                .withMessage("La criticidad debe ser BAJA, MEDIA, ALTA o CRITICA")
                .withCode("400")
                .buildBusinessException();
    }
}