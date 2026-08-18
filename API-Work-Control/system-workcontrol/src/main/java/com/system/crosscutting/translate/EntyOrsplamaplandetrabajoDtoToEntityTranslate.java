package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsplamaplandetrabajo;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO de plan de trabajo hacia entidad.
 */
@Component
public class EntyOrsplamaplandetrabajoDtoToEntityTranslate
        implements Translator<EntyOrsplamaplandetrabajoDto, EntyOrsplamaplandetrabajo> {

    @Override
    public EntyOrsplamaplandetrabajo translate(
            final EntyOrsplamaplandetrabajoDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsplamaplandetrabajo.class
                );
    }
}