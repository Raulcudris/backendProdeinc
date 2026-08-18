package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmdsitiospuntos;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad de sitios/puntos hacia DTO.
 */
@Component
public class EntyOrsordmdsitiospuntosEntityToDtoTranslate
        implements Translator<EntyOrsordmdsitiospuntos, EntyOrsordmdsitiospuntosDto> {

    @Override
    public EntyOrsordmdsitiospuntosDto translate(
            final EntyOrsordmdsitiospuntos input
    ) throws EBusinessException {
        return GsonUtil.getGson()
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsordmdsitiospuntosDto.class
                );
    }
}