package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrssitmdsitioDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrssitmdsitio;
import com.system.crosscutting.utils.GsonUtil;

import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO de sitio hacia entidad.
 */
@Component
public class EntyOrssitmdsitioDtoToEntityTranslate
        implements Translator<EntyOrssitmdsitioDto, EntyOrssitmdsitio> {

    @Override
    public EntyOrssitmdsitio translate(
            final EntyOrssitmdsitioDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(
                GsonUtil.getGson().toJson(input),
                EntyOrssitmdsitio.class
        );
    }
}