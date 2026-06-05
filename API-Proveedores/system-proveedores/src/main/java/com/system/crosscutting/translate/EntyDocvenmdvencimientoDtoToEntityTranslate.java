package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyDocvenmdvencimientoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDocvenmdvencimiento;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO {@link EntyDocvenmdvencimientoDto}
 * hacia la entidad {@link EntyDocvenmdvencimiento}.
 */
@Component
public class EntyDocvenmdvencimientoDtoToEntityTranslate implements Translator<EntyDocvenmdvencimientoDto, EntyDocvenmdvencimiento> {

    /**
     * Convierte un DTO de vencimiento documental hacia su entidad.
     *
     * @param input DTO de vencimiento documental.
     * @return entidad de vencimiento documental.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyDocvenmdvencimiento translate(final EntyDocvenmdvencimientoDto input) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(GsonUtil.getGson().toJson(input), EntyDocvenmdvencimiento.class);
    }
}
