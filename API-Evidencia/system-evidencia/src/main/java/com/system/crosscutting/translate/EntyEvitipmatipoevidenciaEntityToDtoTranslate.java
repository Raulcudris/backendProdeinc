package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEvitipmatipoevidencia;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad {@link EntyEvitipmatipoevidencia}
 * hacia el DTO {@link EntyEvitipmatipoevidenciaDto}.
 */
@Component
public class EntyEvitipmatipoevidenciaEntityToDtoTranslate implements Translator<EntyEvitipmatipoevidencia, EntyEvitipmatipoevidenciaDto> {

    /**
     * Convierte una entidad de tipo de evidencia hacia su DTO.
     *
     * @param input entidad de tipo de evidencia.
     * @return DTO de tipo de evidencia.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEvitipmatipoevidenciaDto translate(final EntyEvitipmatipoevidencia input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyEvitipmatipoevidenciaDto.class);
    }
}
