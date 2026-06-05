package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrssitmdsitioDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrssitmdsitio;
import com.system.crosscutting.utils.GsonUtil;

import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad de sitio hacia DTO.
 */
@Component
public class EntyOrssitmdsitioEntityToDtoTranslate
        implements Translator<EntyOrssitmdsitio, EntyOrssitmdsitioDto> {

    @Override
    public EntyOrssitmdsitioDto translate(
            final EntyOrssitmdsitio input
    ) throws EBusinessException {
        return GsonUtil.getGson().fromJson(
                GsonUtil.getGson().toJson(input),
                EntyOrssitmdsitioDto.class
        );
    }
}