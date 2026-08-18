package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsplamdreporteoperacionDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsplamdreporteoperacion;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

@Component
public class EntyOrsplamdreporteoperacionDtoToEntityTranslate
        implements Translator<EntyOrsplamdreporteoperacionDto, EntyOrsplamdreporteoperacion> {

    @Override
    public EntyOrsplamdreporteoperacion translate(final EntyOrsplamdreporteoperacionDto input)
            throws EBusinessException {

        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsplamdreporteoperacion.class
                );
    }
}