package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsplamddetalleequipooperacionDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsplamddetalleequipooperacion;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

@Component
public class EntyOrsplamddetalleequipooperacionEntityToDtoTranslate
        implements Translator<EntyOrsplamddetalleequipooperacion, EntyOrsplamddetalleequipooperacionDto> {

    @Override
    public EntyOrsplamddetalleequipooperacionDto translate(final EntyOrsplamddetalleequipooperacion input)
            throws EBusinessException {

        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsplamddetalleequipooperacionDto.class
                );
    }
}