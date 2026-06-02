package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyEqumedmaunidadmedidaDto;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEqumedmaunidadmedida;
import org.springframework.stereotype.Component;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.GsonUtil;

/**
 * Traductor encargado de convertir el DTO {@link EntyEqumedmaunidadmedidaDto}
 * hacia la entidad {@link EntyEqumedmaunidadmedida}.
 */
@Component
public class EntyEqumedmaunidadmedidaDtoToEntityTranslate implements Translator<EntyEqumedmaunidadmedidaDto, EntyEqumedmaunidadmedida> {

    /**
     * Convierte un DTO de unidad de medida hacia su entidad.
     *
     * @param input DTO de unidad de medida.
     * @return entidad de unidad de medida.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEqumedmaunidadmedida translate(final EntyEqumedmaunidadmedidaDto input) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(GsonUtil.getGson().toJson(input), EntyEqumedmaunidadmedida.class);
    }
}
