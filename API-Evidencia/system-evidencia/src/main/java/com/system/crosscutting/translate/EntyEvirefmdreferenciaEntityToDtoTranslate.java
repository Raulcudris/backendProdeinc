package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEvirefmdreferencia;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad {@link EntyEvirefmdreferencia}
 * hacia el DTO {@link EntyEvirefmdreferenciaDto}.
 */
@Component
public class EntyEvirefmdreferenciaEntityToDtoTranslate implements Translator<EntyEvirefmdreferencia, EntyEvirefmdreferenciaDto> {

    /**
     * Convierte una entidad de referencia de evidencia hacia su DTO.
     *
     * @param input entidad de referencia de evidencia.
     * @return DTO de referencia de evidencia.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEvirefmdreferenciaDto translate(final EntyEvirefmdreferencia input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyEvirefmdreferenciaDto.class);
    }
}
