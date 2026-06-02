package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyDocdocmadocumentoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDocdocmadocumento;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir la entidad {@link EntyDocdocmadocumento}
 * hacia el DTO {@link EntyDocdocmadocumentoDto}.
 */
@Component
public class EntyDocdocmadocumentoEntityToDtoTranslate implements Translator<EntyDocdocmadocumento, EntyDocdocmadocumentoDto> {

    /**
     * Convierte una entidad de documento hacia su DTO.
     *
     * @param input entidad de documento.
     * @return DTO de documento.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyDocdocmadocumentoDto translate(final EntyDocdocmadocumento input) throws EBusinessException {
        return GsonUtil.getGson().fromJson(GsonUtil.getGson().toJson(input), EntyDocdocmadocumentoDto.class);
    }
}
