package com.system.crosscutting.translate;

import org.springframework.stereotype.Component;

import com.system.crosscutting.domain.model.EntyOrsrdomdreporteDiarioDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsrdomdreporteDiario;
import com.system.crosscutting.utils.GsonUtil;

@Component
public class EntyOrsrdomdreporteDiarioDtoToEntityTranslate
        implements Translator<EntyOrsrdomdreporteDiarioDto, EntyOrsrdomdreporteDiario> {

    @Override
    public EntyOrsrdomdreporteDiario translate(
            final EntyOrsrdomdreporteDiarioDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(
                GsonUtil.getGson().toJson(input),
                EntyOrsrdomdreporteDiario.class
        );
    }
}