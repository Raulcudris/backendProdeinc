package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyEqutipmatipoequipoDto;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEqutipmatipoequipos;
import org.springframework.stereotype.Component;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.GsonUtil;

/**
 * Traductor encargado de convertir el DTO {@link EntyEqutipmatipoequipoDto}
 * hacia la entidad {@link EntyEqutipmatipoequipos}.
 */
@Component
public class EntyEqutipmatipoequipoDtoToEntityTranslate implements Translator<EntyEqutipmatipoequipoDto, EntyEqutipmatipoequipos> {

    /**
     * Convierte un DTO de tipo de equipo hacia su entidad.
     *
     * @param input DTO de tipo de equipo.
     * @return entidad de tipo de equipo.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEqutipmatipoequipos translate(final EntyEqutipmatipoequipoDto input) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(GsonUtil.getGson().toJson(input), EntyEqutipmatipoequipos.class);
    }
}