package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsplamdplantrabsemana;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad de plan trabajo semana hacia DTO.
 */
@Component
public class EntyOrsplamdplantrabsemanaEntityToDtoTranslate
        implements Translator<EntyOrsplamdplantrabsemana, EntyOrsplamdplantrabsemanaDto> {

    @Override
    public EntyOrsplamdplantrabsemanaDto translate(
            final EntyOrsplamdplantrabsemana input
    ) throws EBusinessException {
        return GsonUtil.getGson()
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsplamdplantrabsemanaDto.class
                );
    }
}