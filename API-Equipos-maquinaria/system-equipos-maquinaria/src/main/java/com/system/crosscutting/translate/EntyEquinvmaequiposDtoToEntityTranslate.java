package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyEquinvmaequiposDto;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEquinvmaequipos;
import org.springframework.stereotype.Component;

import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.GsonUtil;

/**
 * Traductor encargado de convertir el DTO {@link EntyEquinvmaequiposDto}
 * hacia la entidad {@link EntyEquinvmaequipos}.
 */
@Component
public class EntyEquinvmaequiposDtoToEntityTranslate implements Translator<EntyEquinvmaequiposDto, EntyEquinvmaequipos> {

    /**
     * Convierte un DTO de equipo hacia su entidad.
     *
     * @param input DTO de equipo.
     * @return entidad de equipo.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEquinvmaequipos translate(final EntyEquinvmaequiposDto input) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(GsonUtil.getGson().toJson(input), EntyEquinvmaequipos.class);
    }
}
