package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyPrvinvmdequipmaquinariaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyPrvinvmdequipmaquinaria;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO de tipo de equipo o maquinaria hacia entidad.
 */
@Component
public class EntyPrvinvmdequipmaquinariaDtoToEntityTranslate
        implements Translator<EntyPrvinvmdequipmaquinariaDto, EntyPrvinvmdequipmaquinaria> {

    @Override
    public EntyPrvinvmdequipmaquinaria translate(
            final EntyPrvinvmdequipmaquinariaDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyPrvinvmdequipmaquinaria.class
                );
    }
}