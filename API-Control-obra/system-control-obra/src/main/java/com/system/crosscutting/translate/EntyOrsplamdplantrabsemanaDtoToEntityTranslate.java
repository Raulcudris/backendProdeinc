package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsplamdplantrabsemana;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO de plan trabajo semana hacia entidad.
 */
@Component
public class EntyOrsplamdplantrabsemanaDtoToEntityTranslate
        implements Translator<EntyOrsplamdplantrabsemanaDto, EntyOrsplamdplantrabsemana> {

    @Override
    public EntyOrsplamdplantrabsemana translate(
            final EntyOrsplamdplantrabsemanaDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsplamdplantrabsemana.class
                );
    }
}