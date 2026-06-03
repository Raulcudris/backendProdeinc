package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEvirefmdreferencia;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO {@link EntyEvirefmdreferenciaDto}
 * hacia la entidad {@link EntyEvirefmdreferencia}.
 */
@Component
public class EntyEvirefmdreferenciaDtoToEntityTranslate implements Translator<EntyEvirefmdreferenciaDto, EntyEvirefmdreferencia> {

    /**
     * Convierte un DTO de referencia de evidencia hacia su entidad.
     *
     * @param input DTO de referencia de evidencia.
     * @return entidad de referencia de evidencia.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEvirefmdreferencia translate(final EntyEvirefmdreferenciaDto input) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(GsonUtil.getGson().toJson(input), EntyEvirefmdreferencia.class);
    }
}
