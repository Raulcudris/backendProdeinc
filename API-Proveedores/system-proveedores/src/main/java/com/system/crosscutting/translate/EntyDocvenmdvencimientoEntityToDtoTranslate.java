package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyDocvenmdvencimientoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDocvenmdvencimiento;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad {@link EntyDocvenmdvencimiento}
 * hacia el DTO {@link EntyDocvenmdvencimientoDto}.
 */
@Component
public class EntyDocvenmdvencimientoEntityToDtoTranslate implements Translator<EntyDocvenmdvencimiento, EntyDocvenmdvencimientoDto> {

    /**
     * Convierte una entidad de vencimiento documental hacia su DTO.
     *
     * @param input entidad de vencimiento documental.
     * @return DTO de vencimiento documental.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyDocvenmdvencimientoDto translate(final EntyDocvenmdvencimiento input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyDocvenmdvencimientoDto.class);
    }
}
