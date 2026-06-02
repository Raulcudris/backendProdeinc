package com.system.modules.documentosobra.usecase;
import com.system.crosscutting.domain.model.EntyDoccatmacategoriaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDoccatmacategoria;
import com.system.crosscutting.persistence.repository.EntyDoccatmacategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Caso de uso para gestionar categorías documentales.
 */
@Service
@RequiredArgsConstructor
public class EntyCategoriaDocumentoService {

    private final EntyDoccatmacategoriaRepository repository;
    private final Translator<EntyDoccatmacategoria, EntyDoccatmacategoriaDto> entityToDtoTranslate;
    private final Translator<EntyDoccatmacategoriaDto, EntyDoccatmacategoria> dtoToEntityTranslate;

    /**
     * Consulta todas las categorías documentales registradas.
     *
     * @return lista de categorías documentales.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyDoccatmacategoriaDto> findAll() throws EBusinessException {
        List<EntyDoccatmacategoriaDto> result = new ArrayList<>();

        for (EntyDoccatmacategoria entity : repository.findAll()) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Guarda una categoría documental.
     *
     * @param dto información de la categoría documental.
     * @return categoría documental guardada.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public EntyDoccatmacategoriaDto save(final EntyDoccatmacategoriaDto dto) throws EBusinessException {
        EntyDoccatmacategoria entity = dtoToEntityTranslate.translate(dto);

        if (entity.getDocEstadoregCado() == null || entity.getDocEstadoregCado().isBlank()) {
            entity.setDocEstadoregCado("1");
        }

        EntyDoccatmacategoria saved = repository.save(entity);
        return entityToDtoTranslate.translate(saved);
    }
}