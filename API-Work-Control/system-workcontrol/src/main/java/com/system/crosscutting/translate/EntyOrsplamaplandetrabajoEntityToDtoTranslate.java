package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsplamaplandetrabajo;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad de plan de trabajo hacia DTO.
 */
@Component
public class EntyOrsplamaplandetrabajoEntityToDtoTranslate
        implements Translator<EntyOrsplamaplandetrabajo, EntyOrsplamaplandetrabajoDto> {

    @Override
    public EntyOrsplamaplandetrabajoDto translate(
            final EntyOrsplamaplandetrabajo input
    ) throws EBusinessException {
        return GsonUtil.getGson()
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsplamaplandetrabajoDto.class
                );
    }
}