package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmaordenservicio;
import org.springframework.stereotype.Component;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.GsonUtil;

/**
 * Traductor encargado de convertir la entidad de orden de servicio hacia DTO.
 */
@Component
public class EntyOrsordmaordenservicioEntityToDtoTranslate implements Translator<EntyOrsordmaordenservicio, EntyOrsordmaordenservicioDto> {

    @Override
    public EntyOrsordmaordenservicioDto translate(final EntyOrsordmaordenservicio input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyOrsordmaordenservicioDto.class);
    }
}