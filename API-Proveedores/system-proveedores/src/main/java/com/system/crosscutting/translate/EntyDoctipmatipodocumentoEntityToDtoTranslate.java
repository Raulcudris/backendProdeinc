package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyDoctipmatipodocumentoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDoctipmatipodocumento;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad {@link EntyDoctipmatipodocumento}
 * hacia el DTO {@link EntyDoctipmatipodocumentoDto}.
 */
@Component
public class EntyDoctipmatipodocumentoEntityToDtoTranslate implements Translator<EntyDoctipmatipodocumento, EntyDoctipmatipodocumentoDto> {

    /**
     * Convierte una entidad de tipo de documento hacia su DTO.
     *
     * @param input entidad de tipo de documento.
     * @return DTO de tipo de documento.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyDoctipmatipodocumentoDto translate(final EntyDoctipmatipodocumento input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyDoctipmatipodocumentoDto.class);
    }
}
