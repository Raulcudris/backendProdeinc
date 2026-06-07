package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsordmdproyecsemanaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmdproyecsemana;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad de proyección semanal hacia DTO.
 */
@Component
public class EntyOrsordmdproyecsemanaEntityToDtoTranslate
        implements Translator<EntyOrsordmdproyecsemana, EntyOrsordmdproyecsemanaDto> {

    @Override
    public EntyOrsordmdproyecsemanaDto translate(
            final EntyOrsordmdproyecsemana input
    ) throws EBusinessException {
        return GsonUtil.getGson()
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsordmdproyecsemanaDto.class
                );
    }
}