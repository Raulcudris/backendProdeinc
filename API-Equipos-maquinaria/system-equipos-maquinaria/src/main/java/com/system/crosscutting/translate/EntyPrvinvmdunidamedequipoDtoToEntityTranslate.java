package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyPrvinvmdunidamedequipoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyPrvinvmdunidamedequipo;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO de unidad de medida de equipo hacia entidad.
 */
@Component
public class EntyPrvinvmdunidamedequipoDtoToEntityTranslate
        implements Translator<EntyPrvinvmdunidamedequipoDto, EntyPrvinvmdunidamedequipo> {

    @Override
    public EntyPrvinvmdunidamedequipo translate(
            final EntyPrvinvmdunidamedequipoDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyPrvinvmdunidamedequipo.class
                );
    }
}