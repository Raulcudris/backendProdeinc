package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsplamdreportediario;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO de reporte diario hacia entidad.
 */
@Component
public class EntyOrsplamdreportediarioDtoToEntityTranslate
        implements Translator<EntyOrsplamdreportediarioDto, EntyOrsplamdreportediario> {

    @Override
    public EntyOrsplamdreportediario translate(
            final EntyOrsplamdreportediarioDto input
    ) throws EBusinessException {
        return GsonUtil.getGson(false)
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsplamdreportediario.class
                );
    }
}