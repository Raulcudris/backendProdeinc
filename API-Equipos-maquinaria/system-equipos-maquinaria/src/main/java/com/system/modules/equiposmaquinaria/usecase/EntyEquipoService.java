package com.system.modules.equiposmaquinaria.usecase;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.system.crosscutting.domain.model.EntyEquinvmaequiposDto;
import com.system.crosscutting.domain.model.EntyEquinvmaequiposResponse;
import com.system.crosscutting.exceptions.ExceptionBuilder;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.persistence.repository.EntyEquinvmaequiposRepository;
import com.system.crosscutting.persistence.repository.EntyEqutipmatipoequipoRepository;
import com.system.modules.equiposmaquinaria.dataproviders.jpa.JpaEquipoDataProviders;
import com.system.modules.equiposmaquinaria.services.UseCase;
import com.system.modules.equiposmaquinaria.services.UsecaseServices;

@UseCase
public class EntyEquipoService
        extends UsecaseServices<EntyEquinvmaequiposDto, JpaEquipoDataProviders> {

    @Autowired
    private JpaEquipoDataProviders jpaDataProviders;

    @Autowired
    private EntyEquinvmaequiposRepository repository;

    @Autowired
    private EntyEqutipmatipoequipoRepository tipoEquipoRepository;

    @PostConstruct
    public void init() {
        this.ijpaDataProvider = jpaDataProviders;
    }

    public EntyEquinvmaequiposResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return this.jpaDataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEquinvmaequiposDto saveBefore(
            EntyEquinvmaequiposDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquIdentifkeyEqui() == null || dto.getEquIdentifkeyEqui().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El código funcional del equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquIdentifkeyTieq() == null || dto.getEquIdentifkeyTieq().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquNombreEqui() == null || dto.getEquNombreEqui().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El nombre del equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (repository.findByEquIdentifkeyEqui(dto.getEquIdentifkeyEqui()).isPresent()) {
            throw ExceptionBuilder.builder()
                    .withMessage("Ya existe un equipo con el código "
                            + dto.getEquIdentifkeyEqui())
                    .withCode("409")
                    .buildBusinessException();
        }

        if (tipoEquipoRepository.findByEquIdentifkeyTieq(dto.getEquIdentifkeyTieq()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el tipo de equipo con el código "
                            + dto.getEquIdentifkeyTieq())
                    .withCode("404")
                    .buildBusinessException();
        }

        validarEstadoOperativo(dto.getEquEstadooperEqui());

        if (dto.getEquEstadooperEqui() == null || dto.getEquEstadooperEqui().isBlank()) {
            dto.setEquEstadooperEqui("1");
        }

        if (dto.getEquEstadoregEqui() == null || dto.getEquEstadoregEqui().isBlank()) {
            dto.setEquEstadoregEqui("1");
        }

        return this.jpaDataProviders.save(dto);
    }

    public EntyEquinvmaequiposDto updateBefore(
            Integer id,
            EntyEquinvmaequiposDto dto
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("La información del equipo es obligatoria")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (dto.getEquIdentifkeyTieq() == null || dto.getEquIdentifkeyTieq().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El tipo de equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        if (tipoEquipoRepository.findByEquIdentifkeyTieq(dto.getEquIdentifkeyTieq()).isEmpty()) {
            throw ExceptionBuilder.builder()
                    .withMessage("No existe el tipo de equipo con el código "
                            + dto.getEquIdentifkeyTieq())
                    .withCode("404")
                    .buildBusinessException();
        }

        if (dto.getEquNombreEqui() == null || dto.getEquNombreEqui().isBlank()) {
            throw ExceptionBuilder.builder()
                    .withMessage("El nombre del equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        validarEstadoOperativo(dto.getEquEstadooperEqui());

        if (dto.getEquEstadooperEqui() == null || dto.getEquEstadooperEqui().isBlank()) {
            dto.setEquEstadooperEqui("1");
        }

        if (dto.getEquEstadoregEqui() == null || dto.getEquEstadoregEqui().isBlank()) {
            dto.setEquEstadoregEqui("1");
        }

        return this.jpaDataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEquinvmaequiposDto equipo = this.jpaDataProviders.get(id);

        if (equipo.getEquPrimarykeyEqui() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El equipo no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        String nextStatus;

        if ("1".equals(estado) || "2".equals(estado)) {
            nextStatus = estado;
        } else {
            nextStatus = "2";
        }

        equipo.setEquEstadoregEqui(nextStatus);

        this.jpaDataProviders.update(id, equipo);

        return "OK";
    }

    public String changeEstadoOperativo(
            Integer id,
            String estadoOperativo
    ) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        validarEstadoOperativo(estadoOperativo);

        EntyEquinvmaequiposDto equipo = this.jpaDataProviders.get(id);

        if (equipo.getEquPrimarykeyEqui() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El equipo no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        equipo.setEquEstadooperEqui(estadoOperativo);

        this.jpaDataProviders.update(id, equipo);

        return "OK";
    }

    public String deleteBefore(Integer id) throws EBusinessException {

        if (id == null || id <= 0) {
            throw ExceptionBuilder.builder()
                    .withMessage("El id del equipo es obligatorio")
                    .withCode("400")
                    .buildBusinessException();
        }

        EntyEquinvmaequiposDto equipo = this.jpaDataProviders.get(id);

        if (equipo.getEquPrimarykeyEqui() == null) {
            throw ExceptionBuilder.builder()
                    .withMessage("El equipo no fue encontrado")
                    .withCode("404")
                    .buildBusinessException();
        }

        equipo.setEquEstadoregEqui("2");

        this.jpaDataProviders.update(id, equipo);

        return "OK";
    }

    private void validarEstadoOperativo(String estadoOperativo) throws EBusinessException {
        if (estadoOperativo == null || estadoOperativo.isBlank()) {
            return;
        }

        if (!"1".equals(estadoOperativo)
                && !"2".equals(estadoOperativo)
                && !"3".equals(estadoOperativo)
                && !"4".equals(estadoOperativo)) {
            throw ExceptionBuilder.builder()
                    .withMessage("El estado operativo debe ser 1=Disponible, 2=Asignado, 3=Mantenimiento o 4=Fuera de servicio")
                    .withCode("400")
                    .buildBusinessException();
        }
    }
}