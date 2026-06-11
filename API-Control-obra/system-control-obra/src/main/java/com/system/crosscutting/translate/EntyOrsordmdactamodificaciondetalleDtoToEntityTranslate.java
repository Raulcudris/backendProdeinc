package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsordmdactamodificaciondetalleDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmdactamodificaciondetalle;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

@Component
public class EntyOrsordmdactamodificaciondetalleDtoToEntityTranslate
        implements Translator<EntyOrsordmdactamodificaciondetalleDto, EntyOrsordmdactamodificaciondetalle> {

    @Override
    public EntyOrsordmdactamodificaciondetalle translate(final EntyOrsordmdactamodificaciondetalleDto input)
            throws EBusinessException {

        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsordmdactamodificaciondetalle.class
                );
    }
}