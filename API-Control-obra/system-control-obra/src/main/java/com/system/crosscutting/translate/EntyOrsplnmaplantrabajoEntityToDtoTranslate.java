package com.system.crosscutting.translate;

import org.springframework.stereotype.Component;

import com.system.crosscutting.domain.model.EntyOrsplnmaplantrabajoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsplnmaplantrabajo;
import com.system.crosscutting.utils.GsonUtil;

@Component
public class EntyOrsplnmaplantrabajoEntityToDtoTranslate
        implements Translator<EntyOrsplnmaplantrabajo, EntyOrsplnmaplantrabajoDto> {

    @Override
    public EntyOrsplnmaplantrabajoDto translate(
            final EntyOrsplnmaplantrabajo input
    ) throws EBusinessException {
        return GsonUtil.getGson().fromJson(
                GsonUtil.getGson().toJson(input),
                EntyOrsplnmaplantrabajoDto.class
        );
    }
}