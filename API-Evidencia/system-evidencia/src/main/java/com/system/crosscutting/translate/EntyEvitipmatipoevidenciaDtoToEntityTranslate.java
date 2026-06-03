package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEvitipmatipoevidencia;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO {@link EntyEvitipmatipoevidenciaDto}
 * hacia la entidad {@link EntyEvitipmatipoevidencia}.
 */
@Component
public class EntyEvitipmatipoevidenciaDtoToEntityTranslate implements Translator<EntyEvitipmatipoevidenciaDto, EntyEvitipmatipoevidencia> {

    /**
     * Convierte un DTO de tipo de evidencia hacia su entidad.
     *
     * @param input DTO de tipo de evidencia.
     * @return entidad de tipo de evidencia.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEvitipmatipoevidencia translate(final EntyEvitipmatipoevidenciaDto input) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(GsonUtil.getGson().toJson(input), EntyEvitipmatipoevidencia.class);
    }
}
