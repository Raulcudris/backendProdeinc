package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyDoccatmacategoriaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDoccatmacategoria;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO {@link EntyDoccatmacategoriaDto}
 * hacia la entidad {@link EntyDoccatmacategoria}.
 */
@Component
public class EntyDoccatmacategoriaDtoToEntityTranslate implements Translator<EntyDoccatmacategoriaDto, EntyDoccatmacategoria> {

    /**
     * Convierte un DTO de categoría documental hacia su entidad.
     *
     * @param input DTO de categoría documental.
     * @return entidad de categoría documental.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyDoccatmacategoria translate(final EntyDoccatmacategoriaDto input) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(GsonUtil.getGson().toJson(input), EntyDoccatmacategoria.class);
    }
}
