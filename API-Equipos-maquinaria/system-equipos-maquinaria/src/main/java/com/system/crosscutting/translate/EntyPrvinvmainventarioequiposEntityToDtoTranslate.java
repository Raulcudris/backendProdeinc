package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyPrvinvmainventarioequiposDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyPrvinvmainventarioequipos;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad de inventario de equipos hacia DTO.
 */
@Component
public class EntyPrvinvmainventarioequiposEntityToDtoTranslate
        implements Translator<EntyPrvinvmainventarioequipos, EntyPrvinvmainventarioequiposDto> {

    @Override
    public EntyPrvinvmainventarioequiposDto translate(
            final EntyPrvinvmainventarioequipos input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyPrvinvmainventarioequiposDto.class
                );
    }
}