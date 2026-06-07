package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsplamdreportediario;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad de reporte diario hacia DTO.
 */
@Component
public class EntyOrsplamdreportediarioEntityToDtoTranslate
        implements Translator<EntyOrsplamdreportediario, EntyOrsplamdreportediarioDto> {

    @Override
    public EntyOrsplamdreportediarioDto translate(
            final EntyOrsplamdreportediario input
    ) throws EBusinessException {
        return GsonUtil.getGson()
                .fromJson(
                        GsonUtil.getGson().toJson(input),
                        EntyOrsplamdreportediarioDto.class
                );
    }
}