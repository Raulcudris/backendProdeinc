package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmdsitiospuntos;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO de sitios/puntos hacia entidad.
 */
@Component
public class EntyOrsordmdsitiospuntosDtoToEntityTranslate
        implements Translator<EntyOrsordmdsitiospuntosDto, EntyOrsordmdsitiospuntos> {

    @Override
    public EntyOrsordmdsitiospuntos translate(
            final EntyOrsordmdsitiospuntosDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsordmdsitiospuntos.class
                );
    }
}