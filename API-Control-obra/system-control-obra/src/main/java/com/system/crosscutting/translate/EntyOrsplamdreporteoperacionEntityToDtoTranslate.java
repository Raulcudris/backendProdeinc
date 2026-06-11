package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsplamdreporteoperacionDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsplamdreporteoperacion;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

@Component
public class EntyOrsplamdreporteoperacionEntityToDtoTranslate
        implements Translator<EntyOrsplamdreporteoperacion, EntyOrsplamdreporteoperacionDto> {

    @Override
    public EntyOrsplamdreporteoperacionDto translate(final EntyOrsplamdreporteoperacion input)
            throws EBusinessException {

        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsplamdreporteoperacionDto.class
                );
    }
}