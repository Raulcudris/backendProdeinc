package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsordmdresumenequiposDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmdresumenequipos;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO de resumen de equipos hacia entidad.
 */
@Component
public class EntyOrsordmdresumenequiposDtoToEntityTranslate
        implements Translator<EntyOrsordmdresumenequiposDto, EntyOrsordmdresumenequipos> {

    @Override
    public EntyOrsordmdresumenequipos translate(
            final EntyOrsordmdresumenequiposDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsordmdresumenequipos.class
                );
    }
}