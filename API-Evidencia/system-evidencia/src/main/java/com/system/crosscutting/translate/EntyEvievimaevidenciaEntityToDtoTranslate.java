package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEvievimaevidencia;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad {@link EntyEvievimaevidencia}
 * hacia el DTO {@link EntyEvievimaevidenciaDto}.
 */
@Component
public class EntyEvievimaevidenciaEntityToDtoTranslate implements Translator<EntyEvievimaevidencia, EntyEvievimaevidenciaDto> {

    /**
     * Convierte una entidad de evidencia hacia su DTO.
     *
     * @param input entidad de evidencia.
     * @return DTO de evidencia.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEvievimaevidenciaDto translate(final EntyEvievimaevidencia input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyEvievimaevidenciaDto.class);
    }
}
