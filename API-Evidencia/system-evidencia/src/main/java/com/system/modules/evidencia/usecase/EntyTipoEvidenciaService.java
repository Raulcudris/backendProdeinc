package com.system.modules.evidencia.usecase;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEvitipmatipoevidencia;
import com.system.crosscutting.persistence.repository.EntyEvitipmatipoevidenciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Caso de uso para gestionar tipos de evidencia.
 */
@Service
@RequiredArgsConstructor
public class EntyTipoEvidenciaService {

    private final EntyEvitipmatipoevidenciaRepository repository;
    private final Translator<EntyEvitipmatipoevidencia, EntyEvitipmatipoevidenciaDto> entityToDtoTranslate;
    private final Translator<EntyEvitipmatipoevidenciaDto, EntyEvitipmatipoevidencia> dtoToEntityTranslate;

    /**
     * Consulta todos los tipos de evidencia registrados.
     *
     * @return lista de tipos de evidencia.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyEvitipmatipoevidenciaDto> findAll() throws EBusinessException {
        List<EntyEvitipmatipoevidenciaDto> result = new ArrayList<>();

        for (EntyEvitipmatipoevidencia entity : repository.findAll()) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Guarda un tipo de evidencia.
     *
     * @param dto información del tipo de evidencia.
     * @return tipo de evidencia guardado.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public EntyEvitipmatipoevidenciaDto save(final EntyEvitipmatipoevidenciaDto dto) throws EBusinessException {
        EntyEvitipmatipoevidencia entity = dtoToEntityTranslate.translate(dto);

        if (entity.getEviEstadoregTiev() == null || entity.getEviEstadoregTiev().isBlank()) {
            entity.setEviEstadoregTiev("1");
        }

        EntyEvitipmatipoevidencia saved = repository.save(entity);
        return entityToDtoTranslate.translate(saved);
    }
}
