package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsconfnovedadhistori;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad de novedad histórica hacia DTO.
 */
@Component
public class EntyOrsconfnovedadhistoriEntityToDtoTranslate
        implements Translator<EntyOrsconfnovedadhistori, EntyOrsconfnovedadhistoriDto> {

    @Override
    public EntyOrsconfnovedadhistoriDto translate(
            final EntyOrsconfnovedadhistori input
    ) throws EBusinessException {
        return GsonUtil.getGson()
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsconfnovedadhistoriDto.class
                );
    }
}