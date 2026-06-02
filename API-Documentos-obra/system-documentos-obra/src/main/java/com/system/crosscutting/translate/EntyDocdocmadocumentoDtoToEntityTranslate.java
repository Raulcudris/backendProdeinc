package com.system.crosscutting.translate;
import com.system.crosscutting.domain.model.EntyDocdocmadocumentoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDocdocmadocumento;
import com.system.crosscutting.utils.GsonUtil;
import org.springframework.stereotype.Component;

/**
 * Traductor encargado de convertir el DTO {@link EntyDocdocmadocumentoDto}
 * hacia la entidad {@link EntyDocdocmadocumento}.
 */
@Component
public class EntyDocdocmadocumentoDtoToEntityTranslate implements Translator<EntyDocdocmadocumentoDto, EntyDocdocmadocumento> {

    /**
     * Convierte un DTO de documento hacia su entidad.
     *
     * @param input DTO de documento.
     * @return entidad de documento.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Override
    public EntyDocdocmadocumento translate(final EntyDocdocmadocumentoDto input) throws EBusinessException {
        return GsonUtil.getGson(false).fromJson(GsonUtil.getGson().toJson(input), EntyDocdocmadocumento.class);
    }
}