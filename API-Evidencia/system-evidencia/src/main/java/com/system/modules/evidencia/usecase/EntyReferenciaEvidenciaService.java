package com.system.modules.evidencia.usecase;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEvirefmdreferencia;
import com.system.crosscutting.persistence.repository.EntyEvirefmdreferenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Caso de uso para gestionar referencias de evidencias hacia otras entidades del sistema.
 */
@Service
@RequiredArgsConstructor
public class EntyReferenciaEvidenciaService {

    private final EntyEvirefmdreferenciaRepository repository;
    private final Translator<EntyEvirefmdreferencia, EntyEvirefmdreferenciaDto> entityToDtoTranslate;
    private final Translator<EntyEvirefmdreferenciaDto, EntyEvirefmdreferencia> dtoToEntityTranslate;

    /**
     * Consulta todas las referencias de evidencias registradas.
     *
     * @return lista de referencias.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyEvirefmdreferenciaDto> findAll() throws EBusinessException {
        List<EntyEvirefmdreferenciaDto> result = new ArrayList<>();

        for (EntyEvirefmdreferencia entity : repository.findAll()) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta referencias asociadas a una evidencia.
     *
     * @param evidenciaKey identificador funcional de la evidencia.
     * @return lista de referencias asociadas a la evidencia.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyEvirefmdreferenciaDto> findByEvidencia(final String evidenciaKey) throws EBusinessException {
        List<EntyEvirefmdreferenciaDto> result = new ArrayList<>();

        for (EntyEvirefmdreferencia entity : repository.findByEviIdentifkeyEvid(evidenciaKey)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta referencias por tipo y código del registro referenciado.
     *
     * @param tipoReferencia tipo de referencia.
     * @param referenciaId código del registro referenciado.
     * @return lista de referencias encontradas.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyEvirefmdreferenciaDto> findByReferencia(final String tipoReferencia, final String referenciaId) throws EBusinessException {
        List<EntyEvirefmdreferenciaDto> result = new ArrayList<>();

        for (EntyEvirefmdreferencia entity : repository.findByEviTiporeferenEvreAndEviReferenciaidEvre(tipoReferencia, referenciaId)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Guarda una referencia de evidencia.
     *
     * @param dto información de la referencia de evidencia.
     * @return referencia de evidencia guardada.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public EntyEvirefmdreferenciaDto save(final EntyEvirefmdreferenciaDto dto) throws EBusinessException {
        EntyEvirefmdreferencia entity = dtoToEntityTranslate.translate(dto);

        if (entity.getEviEstadoregEvre() == null || entity.getEviEstadoregEvre().isBlank()) {
            entity.setEviEstadoregEvre("1");
        }

        EntyEvirefmdreferencia saved = repository.save(entity);
        return entityToDtoTranslate.translate(saved);
    }
}
