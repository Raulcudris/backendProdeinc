package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyEqutipmatipoequipoDto;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEqutipmatipoequipos;
import org.springframework.stereotype.Component;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.GsonUtil;

/**
 * Traductor encargado de convertir la entidad {@link EntyEqutipmatipoequipos}
 * hacia el DTO {@link EntyEqutipmatipoequipoDto}.
 */
@Component
public class EntyEqutipmatipoequipoEntityToDtoTranslate implements Translator<EntyEqutipmatipoequipos, EntyEqutipmatipoequipoDto> {

    /**
     * Convierte una entidad de tipo de equipo hacia su DTO.
     *
     * @param input entidad de tipo de equipo.
     * @return DTO de tipo de equipo.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEqutipmatipoequipoDto translate(final EntyEqutipmatipoequipos input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyEqutipmatipoequipoDto.class);
    }
}
