package com.system.crosscutting.translate;

import com.system.crosscutting.domain.model.EntyEquasimdasignequipoDto;
import com.system.crosscutting.persistence.entity.EntyEquasimdasignequipo;
import org.springframework.stereotype.Component;

import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.utils.GsonUtil;

/**
 * Traductor encargado de convertir la entidad {@link EntyEquasimdasignequipo}
 * hacia el DTO {@link EntyEquasimdasignequipoDto}.
 */
@Component
public class EntyEquasimdasignequipoEntityToDtoTranslate implements Translator<EntyEquasimdasignequipo, EntyEquasimdasignequipoDto> {

    /**
     * Convierte una entidad de asignación de equipo hacia su DTO.
     *
     * @param input entidad de asignación de equipo.
     * @return DTO de asignación de equipo.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEquasimdasignequipoDto translate(final EntyEquasimdasignequipo input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyEquasimdasignequipoDto.class);
    }
}
