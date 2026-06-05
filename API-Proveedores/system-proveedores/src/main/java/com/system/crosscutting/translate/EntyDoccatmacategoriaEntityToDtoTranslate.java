package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyDoccatmacategoriaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDoccatmacategoria;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad {@link EntyDoccatmacategoria}
 * hacia el DTO {@link EntyDoccatmacategoriaDto}.
 */
@Component
public class EntyDoccatmacategoriaEntityToDtoTranslate implements Translator<EntyDoccatmacategoria, EntyDoccatmacategoriaDto> {

    /**
     * Convierte una entidad de categoría documental hacia su DTO.
     *
     * @param input entidad de categoría documental.
     * @return DTO de categoría documental.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyDoccatmacategoriaDto translate(final EntyDoccatmacategoria input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyDoccatmacategoriaDto.class);
    }
}
