package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyPrvinvmdunidamedequipoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyPrvinvmdunidamedequipo;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad de unidad de medida de equipo hacia DTO.
 */
@Component
public class EntyPrvinvmdunidamedequipoEntityToDtoTranslate
        implements Translator<EntyPrvinvmdunidamedequipo, EntyPrvinvmdunidamedequipoDto> {

    @Override
    public EntyPrvinvmdunidamedequipoDto translate(
            final EntyPrvinvmdunidamedequipo input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyPrvinvmdunidamedequipoDto.class
                );
    }
}