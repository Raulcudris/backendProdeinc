package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyEquinvmaequiposDto;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEquinvmaequipos;
import org.springframework.stereotype.Component;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.GsonUtil;

/**
 * Traductor encargado de convertir la entidad {@link EntyEquinvmaequipos}
 * hacia el DTO {@link EntyEquinvmaequiposDto}.
 */
@Component
public class EntyEquinvmaequiposEntityToDtoTranslate implements Translator<EntyEquinvmaequipos, EntyEquinvmaequiposDto> {

    /**
     * Convierte una entidad de equipo hacia su DTO.
     *
     * @param input entidad de equipo.
     * @return DTO de equipo.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEquinvmaequiposDto translate(final EntyEquinvmaequipos input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyEquinvmaequiposDto.class);
    }
}
