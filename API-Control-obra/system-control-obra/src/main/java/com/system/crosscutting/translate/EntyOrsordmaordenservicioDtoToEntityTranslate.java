package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmaordenservicio;
import org.springframework.stereotype.Component;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.GsonUtil;

/**
 * Traductor encargado de convertir el DTO {@link EntyOrsordmaordenservicioDto}
 * hacia la entidad {@link EntyOrsordmaordenservicio}.
 */
@Component
public class EntyOrsordmaordenservicioDtoToEntityTranslate implements Translator<EntyOrsordmaordenservicioDto, EntyOrsordmaordenservicio> {

    /**
     * Convierte un DTO de orden de servicio hacia su entidad.
     *
     * @param input DTO de orden de servicio.
     * @return entidad de orden de servicio.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyOrsordmaordenservicio translate(final EntyOrsordmaordenservicioDto input) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(GsonUtil.getGson().toJson(input), EntyOrsordmaordenservicio.class);
    }
}