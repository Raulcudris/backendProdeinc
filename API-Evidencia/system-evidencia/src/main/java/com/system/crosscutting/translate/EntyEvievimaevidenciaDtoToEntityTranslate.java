package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEvievimaevidencia;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO {@link EntyEvievimaevidenciaDto}
 * hacia la entidad {@link EntyEvievimaevidencia}.
 */
@Component
public class EntyEvievimaevidenciaDtoToEntityTranslate implements Translator<EntyEvievimaevidenciaDto, EntyEvievimaevidencia> {

    /**
     * Convierte un DTO de evidencia hacia su entidad.
     *
     * @param input DTO de evidencia.
     * @return entidad de evidencia.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEvievimaevidencia translate(final EntyEvievimaevidenciaDto input) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(GsonUtil.getGson().toJson(input), EntyEvievimaevidencia.class);
    }
}
