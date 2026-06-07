package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsordmdproyecsemanaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmdproyecsemana;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO de proyección semanal hacia entidad.
 */
@Component
public class EntyOrsordmdproyecsemanaDtoToEntityTranslate
        implements Translator<EntyOrsordmdproyecsemanaDto, EntyOrsordmdproyecsemana> {

    @Override
    public EntyOrsordmdproyecsemana translate(
            final EntyOrsordmdproyecsemanaDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsordmdproyecsemana.class
                );
    }
}