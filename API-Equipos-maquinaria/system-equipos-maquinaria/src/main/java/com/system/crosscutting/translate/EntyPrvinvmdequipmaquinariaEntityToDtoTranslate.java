package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyPrvinvmdequipmaquinariaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyPrvinvmdequipmaquinaria;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad de tipo de equipo o maquinaria hacia DTO.
 */
@Component
public class EntyPrvinvmdequipmaquinariaEntityToDtoTranslate
        implements Translator<EntyPrvinvmdequipmaquinaria, EntyPrvinvmdequipmaquinariaDto> {

    @Override
    public EntyPrvinvmdequipmaquinariaDto translate(
            final EntyPrvinvmdequipmaquinaria input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyPrvinvmdequipmaquinariaDto.class
                );
    }
}