package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyEqumedmaunidadmedidaDto;
import com.system.crosscutting.persistence.entity.EntyEqumedmaunidadmedida;
import org.springframework.stereotype.Component;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.utils.GsonUtil;

/**
 * Traductor encargado de convertir la entidad {@link EntyEqumedmaunidadmedida}
 * hacia el DTO {@link EntyEqumedmaunidadmedidaDto}.
 */
@Component
public class EntyEqumedmaunidadmedidaEntityToDtoTranslate implements Translator<EntyEqumedmaunidadmedida, EntyEqumedmaunidadmedidaDto> {

    /**
     * Convierte una entidad de unidad de medida hacia su DTO.
     *
     * @param input entidad de unidad de medida.
     * @return DTO de unidad de medida.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyEqumedmaunidadmedidaDto translate(final EntyEqumedmaunidadmedida input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyEqumedmaunidadmedidaDto.class);
    }
}