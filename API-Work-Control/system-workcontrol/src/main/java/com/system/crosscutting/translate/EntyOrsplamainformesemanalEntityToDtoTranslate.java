package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsplamainformesemanalDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsplamainformesemanal;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

@Component
public class EntyOrsplamainformesemanalEntityToDtoTranslate
        implements Translator<EntyOrsplamainformesemanal, EntyOrsplamainformesemanalDto> {

    @Override
    public EntyOrsplamainformesemanalDto translate(final EntyOrsplamainformesemanal input)
            throws EBusinessException {

        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsplamainformesemanalDto.class
                );
    }
}