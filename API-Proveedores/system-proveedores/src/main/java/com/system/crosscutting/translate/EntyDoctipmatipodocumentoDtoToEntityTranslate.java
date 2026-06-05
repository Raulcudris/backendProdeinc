package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyDoctipmatipodocumentoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDoctipmatipodocumento;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO {@link EntyDoctipmatipodocumentoDto}
 * hacia la entidad {@link EntyDoctipmatipodocumento}.
 */
@Component
public class EntyDoctipmatipodocumentoDtoToEntityTranslate implements Translator<EntyDoctipmatipodocumentoDto, EntyDoctipmatipodocumento> {

    /**
     * Convierte un DTO de tipo de documento hacia su entidad.
     *
     * @param input DTO de tipo de documento.
     * @return entidad de tipo de documento.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyDoctipmatipodocumento translate(final EntyDoctipmatipodocumentoDto input) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(GsonUtil.getGson().toJson(input), EntyDoctipmatipodocumento.class);
    }
}
