package com.system.modules.equiposmaquinaria.usecase;

import java.time.LocalDate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyEquasimdasignequipoDto;
import com.system.crosscutting.domain.model.EntyEquasimdasignequipoResponse;
import com.system.crosscutting.domain.model.EntyEquinvmaequiposDto;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyEquasimdasignequipoRepository;
import com.system.crosscutting.persistence.repository.EntyEquinvmaequiposRepository;
import com.system.modules.equiposmaquinaria.dataproviders.jpa.JpaAsignacionEquipoDataProviders;
import com.system.modules.equiposmaquinaria.dataproviders.jpa.JpaEquipoDataProviders;
import com.system.modules.equiposmaquinaria.services.UseCase;
import com.system.modules.equiposmaquinaria.services.UsecaseServices;

@UseCase
public class EntyAsignacionEquipoService
        extends UsecaseServices<EntyEquasimdasignequipoDto, JpaAsignacionEquipoDataProviders> {

    @Autowired
    private JpaAsignacionEquipoDataProviders jpaDataProviders;

    @Autowired
    private JpaEquipoDataProviders equipoDataProviders;

    @Autowired
    private EntyEquasimdasignequipoRepository repository;

    @Autowired
    private EntyEquinvmaequiposRepository equipoRepository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyEquasimdasignequipoResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEquasimdasignequipoResponse getByEquipo(
            int currentPage,
            int pageSize,
            String equipoKey
    ) throws EBusinessException {
        if (equipoKey == null || equipoKey.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        return this.jpaDataProviders.getByEquipo(currentPage, pageSize, equipoKey);
    }

    public EntyEquasimdasignequipoResponse getByOrden(
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

    public EntyEquasimdasignequipoResponse getByResponsable(
            int currentPage,
            int pageSize,
            String responsable
    ) throws EBusinessException {
        if (responsable == null || responsable.isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El responsable es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        return this.jpaDataProviders.getByResponsable(currentPage, pageSize, responsable);
    }

    public EntyEquasimdasignequipoDto saveBefore(
            EntyEquasimdasignequipoDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La asignación de equipo es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquIdentifkeyAseq() == null || dto.getEquIdentifkeyAseq().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional de la asignación es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquIdentifkeyEqui() == null || dto.getEquIdentifkeyEqui().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquFechaasigAseq() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La fecha de asignación es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByEquIdentifkeyAseq(dto.getEquIdentifkeyAseq()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe una asignación con el código "
                            + dto.getEquIdentifkeyAseq())
                    .withCode("409")
                    .buildBusinessException();
        }

        EntyEquinvmaequiposDto equipo = obtenerEquipoPorCodigo(dto.getEquIdentifkeyEqui());

        if (equipo.getEquPrimarykeyEqui() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el equipo con el código "
                            + dto.getEquIdentifkeyEqui())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (!"1".equals(equipo.getEquEstadooperEqui())) {
            throw ExceptionBuilder.builder()
                    .withMessage("El equipo no está disponible para asignación")
                    .withCode("409")
                    .buildBusinessException();
        }

        if (dto.getEquEstadoregAseq() == null || dto.getEquEstadoregAseq().isBlank()) {
            dto.setEquEstadoregAseq("1");
        }

        EntyEquasimdasignequipoDto saved = this.jpaDataProviders.save(dto);

        equipo.setEquEstadooperEqui("2");
        equipoDataProviders.update(equipo.getEquPrimarykeyEqui(), equipo);

        return saved;
    }

    public EntyEquasimdasignequipoDto updateBefore(
            Integer id,
            EntyEquasimdasignequipoDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la asignación es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información de la asignación es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquIdentifkeyEqui() == null || dto.getEquIdentifkeyEqui().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código del equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquFechaasigAseq() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La fecha de asignación es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquFechadevolAseq() != null
                && dto.getEquFechadevolAseq().isBefore(dto.getEquFechaasigAseq())) {
            throw ExceptionBuilder.builder()
                    .withMessage("La fecha de devolución no puede ser menor que la fecha de asignación")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquEstadoregAseq() == null || dto.getEquEstadoregAseq().isBlank()) {
            dto.setEquEstadoregAseq("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String cerrarAsignacion(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la asignación es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEquasimdasignequipoDto asignacion = this.jpaDataProviders.get(id);

        if (asignacion.getEquPrimarykeyAseq() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La asignación no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        asignacion.setEquEstadoregAseq("3");
        asignacion.setEquFechadevolAseq(LocalDate.now());

        this.jpaDataProviders.update(id, asignacion);

        EntyEquinvmaequiposDto equipo = obtenerEquipoPorCodigo(asignacion.getEquIdentifkeyEqui());

        if (equipo.getEquPrimarykeyEqui() != null) {
            equipo.setEquEstadooperEqui("1");
            equipoDataProviders.update(equipo.getEquPrimarykeyEqui(), equipo);
        }

        return "OK";
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la asignación es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (!"1".equals(estado) && !"2".equals(estado) && !"3".equals(estado)) {
            throw ExceptionBuilder.builder()
                    .withMessage("El estado de la asignación debe ser 1=Activo, 2=Inactivo o 3=Cerrado")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEquasimdasignequipoDto asignacion = this.jpaDataProviders.get(id);

        if (asignacion.getEquPrimarykeyAseq() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La asignación no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        asignacion.setEquEstadoregAseq(estado);

        if ("3".equals(estado) && asignacion.getEquFechadevolAseq() == null) {
            asignacion.setEquFechadevolAseq(LocalDate.now());
        }

        this.jpaDataProviders.update(id, asignacion);

        if ("3".equals(estado)) {
            EntyEquinvmaequiposDto equipo = obtenerEquipoPorCodigo(asignacion.getEquIdentifkeyEqui());

            if (equipo.getEquPrimarykeyEqui() != null) {
                equipo.setEquEstadooperEqui("1");
                equipoDataProviders.update(equipo.getEquPrimarykeyEqui(), equipo);
            }
        }

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id de la asignación es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEquasimdasignequipoDto asignacion = this.jpaDataProviders.get(id);

        if (asignacion.getEquPrimarykeyAseq() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La asignación no fue encontrada")
                    .withCode("404")
                    .buildBusinessException();
        }

        asignacion.setEquEstadoregAseq("2");

        this.jpaDataProviders.update(id, asignacion);

        return "OK";
    }

    private EntyEquinvmaequiposDto obtenerEquipoPorCodigo(String equipoKey) throws EBusinessException {
        return equipoRepository.findByEquIdentifkeyEqui(equipoKey)
                .map(entity -> {
                    EntyEquinvmaequiposDto dto = new EntyEquinvmaequiposDto();

                    dto.setEquPrimarykeyEqui(entity.getEquPrimarykeyEqui());
                    dto.setEquIdentifkeyEqui(entity.getEquIdentifkeyEqui());
                    dto.setEquIdentifkeyTieq(entity.getEquIdentifkeyTieq());
                    dto.setPrvIdentifkeyMprv(entity.getPrvIdentifkeyMprv());
                    dto.setEquCodinternoEqui(entity.getEquCodinternoEqui());
                    dto.setEquNombreEqui(entity.getEquNombreEqui());
                    dto.setEquMarcaEqui(entity.getEquMarcaEqui());
                    dto.setEquModeloEqui(entity.getEquModeloEqui());
                    dto.setEquPlacaEqui(entity.getEquPlacaEqui());
                    dto.setEquSerialEqui(entity.getEquSerialEqui());
                    dto.setEquEstadooperEqui(entity.getEquEstadooperEqui());
                    dto.setEquEstadoregEqui(entity.getEquEstadoregEqui());

                    return dto;
                })
                .orElse(new EntyEquinvmaequiposDto());
    }
}