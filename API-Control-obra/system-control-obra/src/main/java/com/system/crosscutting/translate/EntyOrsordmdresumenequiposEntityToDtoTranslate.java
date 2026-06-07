package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsordmdresumenequiposDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmdresumenequipos;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad de resumen de equipos hacia DTO.
 */
@Component
public class EntyOrsordmdresumenequiposEntityToDtoTranslate
        implements Translator<EntyOrsordmdresumenequipos, EntyOrsordmdresumenequiposDto> {

    @Override
    public EntyOrsordmdresumenequiposDto translate(
            final EntyOrsordmdresumenequipos input
    ) throws EBusinessException {
        return GsonUtil.getGson()
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsordmdresumenequiposDto.class
                );
    }
}