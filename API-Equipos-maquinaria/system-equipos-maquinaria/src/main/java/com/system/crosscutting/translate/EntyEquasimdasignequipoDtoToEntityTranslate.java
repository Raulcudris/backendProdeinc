package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyEquasimdasignequipoDto;
import com.system.crosscutting.persistence.entity.EntyEquasimdasignequipo;
import org.springframework.stereotype.Component;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.utils.GsonUtil;

/**
 * Traductor encargado de convertir el DTO {@link EntyEquasimdasignequipoDto}
 * hacia la entidad {@link EntyEquasimdasignequipo}.
 */
@Component
public class EntyEquasimdasignequipoDtoToEntityTranslate implements Translator<EntyEquasimdasignequipoDto, EntyEquasimdasignequipo> {

    /**
     * Convierte un DTO de asignación de equipo hacia su entidad.
     *
     * @param input DTO de asignación de equipo.
     * @return entidad de asignación de equipo.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEquasimdasignequipo translate(final EntyEquasimdasignequipoDto input) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(GsonUtil.getGson().toJson(input), EntyEquasimdasignequipo.class);
    }
}