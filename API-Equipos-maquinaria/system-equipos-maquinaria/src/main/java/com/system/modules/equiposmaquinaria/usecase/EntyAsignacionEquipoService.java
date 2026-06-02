package com.system.modules.equiposmaquinaria.usecase;

import java.util.ArrayList;
import java.util.List;

import com.system.crosscutting.domain.model.EntyEquasimdasignequipoDto;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEquasimdasignequipo;
import com.system.crosscutting.persistence.repository.EntyEquasimdasignequipoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.system.crosscutting.exceptions.Main.EBusinessException;

import lombok.RequiredArgsConstructor;

/**
 * Caso de uso para gestionar asignaciones de equipos, maquinaria, vehículos o herramientas.
 */
@Service
@RequiredArgsConstructor
public class EntyAsignacionEquipoService {

    private final EntyEquasimdasignequipoRepository repository;
    private final Translator<EntyEquasimdasignequipo, EntyEquasimdasignequipoDto> entityToDtoTranslate;
    private final Translator<EntyEquasimdasignequipoDto, EntyEquasimdasignequipo> dtoToEntityTranslate;

    /**
     * Consulta todas las asignaciones de equipos registradas.
     *
     * @return lista de asignaciones.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional(readOnly = true)
    public List<EntyEquasimdasignequipoDto> findAll() throws EBusinessException {
        List<EntyEquasimdasignequipoDto> result = new ArrayList<>();

        for (EntyEquasimdasignequipo entity : repository.findAll()) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta asignaciones por equipo.
     *
     * @param equipoKey identificador funcional del equipo.
     * @return lista de asignaciones del equipo.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional(readOnly = true)
    public List<EntyEquasimdasignequipoDto> findByEquipo(final String equipoKey) throws EBusinessException {
        List<EntyEquasimdasignequipoDto> result = new ArrayList<>();

        for (EntyEquasimdasignequipo entity : repository.findByEquIdentifkeyEqui(equipoKey)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta asignaciones por orden de servicio.
     *
     * @param ordenKey identificador funcional de la orden de servicio.
     * @return lista de asignaciones asociadas a la orden.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional(readOnly = true)
    public List<EntyEquasimdasignequipoDto> findByOrden(final String ordenKey) throws EBusinessException {
        List<EntyEquasimdasignequipoDto> result = new ArrayList<>();

        for (EntyEquasimdasignequipo entity : repository.findByOrsIdentifkeyOrde(ordenKey)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta asignaciones por plan de trabajo.
     *
     * @param planKey identificador funcional del plan de trabajo.
     * @return lista de asignaciones asociadas al plan.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional(readOnly = true)
    public List<EntyEquasimdasignequipoDto> findByPlan(final String planKey) throws EBusinessException {
        List<EntyEquasimdasignequipoDto> result = new ArrayList<>();

        for (EntyEquasimdasignequipo entity : repository.findByOrsIdentifkeyPltr(planKey)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Guarda una asignación de equipo.
     *
     * @param dto información de la asignación de equipo.
     * @return asignación de equipo guardada.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public EntyEquasimdasignequipoDto save(final EntyEquasimdasignequipoDto dto) throws EBusinessException {
        EntyEquasimdasignequipo entity = dtoToEntityTranslate.translate(dto);

        if (entity.getEquEstadoregAseq() == null || entity.getEquEstadoregAseq().isBlank()) {
            entity.setEquEstadoregAseq("1");
        }

        EntyEquasimdasignequipo saved = repository.save(entity);
        return entityToDtoTranslate.translate(saved);
    }
}