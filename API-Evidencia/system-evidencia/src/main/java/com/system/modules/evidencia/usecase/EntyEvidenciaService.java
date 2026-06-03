package com.system.modules.evidencia.usecase;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEvievimaevidencia;
import com.system.crosscutting.persistence.repository.EntyEvievimaevidenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Caso de uso para gestionar evidencias del sistema.
 */
@Service
@RequiredArgsConstructor
public class EntyEvidenciaService {

    private final EntyEvievimaevidenciaRepository repository;
    private final Translator<EntyEvievimaevidencia, EntyEvievimaevidenciaDto> entityToDtoTranslate;
    private final Translator<EntyEvievimaevidenciaDto, EntyEvievimaevidencia> dtoToEntityTranslate;

    /**
     * Consulta todas las evidencias registradas.
     *
     * @return lista de evidencias.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyEvievimaevidenciaDto> findAll() throws EBusinessException {
        List<EntyEvievimaevidenciaDto> result = new ArrayList<>();

        for (EntyEvievimaevidencia entity : repository.findAll()) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta evidencias por tipo.
     *
     * @param tipoEvidenciaKey identificador funcional del tipo de evidencia.
     * @return lista de evidencias encontradas.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyEvievimaevidenciaDto> findByTipoEvidencia(final String tipoEvidenciaKey) throws EBusinessException {
        List<EntyEvievimaevidenciaDto> result = new ArrayList<>();

        for (EntyEvievimaevidencia entity : repository.findByEviIdentifkeyTiev(tipoEvidenciaKey)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta evidencias por usuario creador.
     *
     * @param usuarioCrea usuario que registró la evidencia.
     * @return lista de evidencias encontradas.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyEvievimaevidenciaDto> findByUsuarioCrea(final String usuarioCrea) throws EBusinessException {
        List<EntyEvievimaevidenciaDto> result = new ArrayList<>();

        for (EntyEvievimaevidencia entity : repository.findByEviUsuariocreaEvid(usuarioCrea)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Guarda una evidencia.
     *
     * @param dto información de la evidencia.
     * @return evidencia guardada.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public EntyEvievimaevidenciaDto save(final EntyEvievimaevidenciaDto dto) throws EBusinessException {
        EntyEvievimaevidencia entity = dtoToEntityTranslate.translate(dto);

        if (entity.getEviFechacapturaEvid() == null) {
            entity.setEviFechacapturaEvid(LocalDateTime.now());
        }

        if (entity.getEviEstadoregEvid() == null || entity.getEviEstadoregEvid().isBlank()) {
            entity.setEviEstadoregEvid("1");
        }

        EntyEvievimaevidencia saved = repository.save(entity);
        return entityToDtoTranslate.translate(saved);
    }
}
