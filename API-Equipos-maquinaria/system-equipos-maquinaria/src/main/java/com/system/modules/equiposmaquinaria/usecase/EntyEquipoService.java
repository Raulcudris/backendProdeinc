package com.system.modules.equiposmaquinaria.usecase;
import java.util.ArrayList;
import java.util.List;
import com.system.crosscutting.domain.model.EntyEquinvmaequiposDto;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEquinvmaequipos;
import com.system.crosscutting.persistence.repository.EntyEquinvmaequiposRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.system.crosscutting.exceptions.Main.EBusinessException;

import lombok.RequiredArgsConstructor;

/**
 * Caso de uso para gestionar equipos, maquinaria, vehículos y herramientas.
 */
@Service
@RequiredArgsConstructor
public class EntyEquipoService {

    private final EntyEquinvmaequiposRepository repository;
    private final Translator<EntyEquinvmaequipos, EntyEquinvmaequiposDto> entityToDtoTranslate;
    private final Translator<EntyEquinvmaequiposDto, EntyEquinvmaequipos> dtoToEntityTranslate;

    /**
     * Consulta todos los equipos registrados.
     *
     * @return lista de equipos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional(readOnly = true)
    public List<EntyEquinvmaequiposDto> findAll() throws EBusinessException {
        List<EntyEquinvmaequiposDto> result = new ArrayList<>();

        for (EntyEquinvmaequipos entity : repository.findAll()) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta equipos por tipo.
     *
     * @param tipoEquipoKey identificador funcional del tipo de equipo.
     * @return lista de equipos encontrados.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional(readOnly = true)
    public List<EntyEquinvmaequiposDto> findByTipoEquipo(final String tipoEquipoKey) throws EBusinessException {
        List<EntyEquinvmaequiposDto> result = new ArrayList<>();

        for (EntyEquinvmaequipos entity : repository.findByEquIdentifkeyTieq(tipoEquipoKey)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta equipos por estado operativo.
     *
     * @param estadoOperativo estado operativo del equipo.
     * @return lista de equipos encontrados.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional(readOnly = true)
    public List<EntyEquinvmaequiposDto> findByEstadoOperativo(final String estadoOperativo) throws EBusinessException {
        List<EntyEquinvmaequiposDto> result = new ArrayList<>();

        for (EntyEquinvmaequipos entity : repository.findByEquEstadooperEqui(estadoOperativo)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Guarda un equipo.
     *
     * @param dto información del equipo.
     * @return equipo guardado.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public EntyEquinvmaequiposDto save(final EntyEquinvmaequiposDto dto) throws EBusinessException {
        EntyEquinvmaequipos entity = dtoToEntityTranslate.translate(dto);

        if (entity.getEquEstadooperEqui() == null || entity.getEquEstadooperEqui().isBlank()) {
            entity.setEquEstadooperEqui("1");
        }

        if (entity.getEquEstadoregEqui() == null || entity.getEquEstadoregEqui().isBlank()) {
            entity.setEquEstadoregEqui("1");
        }

        EntyEquinvmaequipos saved = repository.save(entity);
        return entityToDtoTranslate.translate(saved);
    }
}