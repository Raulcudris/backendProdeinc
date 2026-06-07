package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyPrvinvmainventarioequiposDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyPrvinvmainventarioequipos;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO de inventario de equipos hacia entidad.
 */
@Component
public class EntyPrvinvmainventarioequiposDtoToEntityTranslate
        implements Translator<EntyPrvinvmainventarioequiposDto, EntyPrvinvmainventarioequipos> {

    @Override
    public EntyPrvinvmainventarioequipos translate(
            final EntyPrvinvmainventarioequiposDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyPrvinvmainventarioequipos.class
                );
    }
}