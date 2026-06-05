package com.system.crosscutting.translate;

import org.springframework.stereotype.Component;

import com.system.crosscutting.domain.model.EntyOrsrdomdreporteDiarioDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsrdomdreporteDiario;
import com.system.crosscutting.utils.GsonUtil;

@Component
public class EntyOrsrdomdreporteDiarioEntityToDtoTranslate
        implements Translator<EntyOrsrdomdreporteDiario, EntyOrsrdomdreporteDiarioDto> {

    @Override
    public EntyOrsrdomdreporteDiarioDto translate(
            final EntyOrsrdomdreporteDiario input
    ) throws EBusinessException {
        return GsonUtil.getGson().fromJson(
                GsonUtil.getGson().toJson(input),
                EntyOrsrdomdreporteDiarioDto.class
        );
    }
}