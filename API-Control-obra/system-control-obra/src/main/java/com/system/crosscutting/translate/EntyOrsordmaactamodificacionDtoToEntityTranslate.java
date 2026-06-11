package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsordmaactamodificacionDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmaactamodificacion;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

@Component
public class EntyOrsordmaactamodificacionDtoToEntityTranslate
        implements Translator<EntyOrsordmaactamodificacionDto, EntyOrsordmaactamodificacion> {

    @Override
    public EntyOrsordmaactamodificacion translate(final EntyOrsordmaactamodificacionDto input)
            throws EBusinessException {

        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsordmaactamodificacion.class
                );
    }
}