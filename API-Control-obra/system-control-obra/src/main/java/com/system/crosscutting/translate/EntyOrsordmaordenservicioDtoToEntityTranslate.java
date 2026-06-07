package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmaordenservicio;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO de orden de servicio hacia entidad.
 */
@Component
public class EntyOrsordmaordenservicioDtoToEntityTranslate
        implements Translator<EntyOrsordmaordenservicioDto, EntyOrsordmaordenservicio> {

    @Override
    public EntyOrsordmaordenservicio translate(
            final EntyOrsordmaordenservicioDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsordmaordenservicio.class
                );
    }
}