package com.system.crosscutting.translate;

import org.springframework.stereotype.Component;

import com.system.crosscutting.domain.model.EntyOrsnovmdnovedadDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsnovmdnovedad;
import com.system.crosscutting.utils.GsonUtil;

@Component
public class EntyOrsnovmdnovedadEntityToDtoTranslate
        implements Translator<EntyOrsnovmdnovedad, EntyOrsnovmdnovedadDto> {

    @Override
    public EntyOrsnovmdnovedadDto translate(
            final EntyOrsnovmdnovedad input
    ) throws EBusinessException {
        return GsonUtil.getGson().fromJson(
                GsonUtil.getGson().toJson(input),
                EntyOrsnovmdnovedadDto.class
        );
    }
}