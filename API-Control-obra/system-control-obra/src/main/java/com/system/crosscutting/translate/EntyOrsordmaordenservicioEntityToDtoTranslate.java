package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmaordenservicio;
import org.springframework.stereotype.Component;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.GsonUtil;

/**
 * Traductor encargado de convertir la entidad {@link EntyOrsordmaordenservicio}
 * hacia el DTO {@link EntyOrsordmaordenservicioDto}.
 */
@Component
public class EntyOrsordmaordenservicioEntityToDtoTranslate implements Translator<EntyOrsordmaordenservicio, EntyOrsordmaordenservicioDto> {

    /**
     * Convierte una entidad de orden de servicio hacia su DTO.
     *
     * @param input entidad de orden de servicio.
     * @return DTO de orden de servicio.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyOrsordmaordenservicioDto translate(final EntyOrsordmaordenservicio input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyOrsordmaordenservicioDto.class);
    }
}
