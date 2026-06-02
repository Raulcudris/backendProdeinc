package com.system.modules.equiposmaquinaria.usecase;
import java.util.ArrayList;
import java.util.List;
import com.system.crosscutting.domain.model.EntyEqumedmaunidadmedidaDto;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEqumedmaunidadmedida;
import com.system.crosscutting.persistence.repository.EntyEqumedmaunidadmedidaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import lombok.RequiredArgsConstructor;

/**
 * Caso de uso para gestionar unidades de medida.
 */
@Service
@RequiredArgsConstructor
public class EntyUnidadMedidaService {

    private final EntyEqumedmaunidadmedidaRepository repository;
    private final Translator<EntyEqumedmaunidadmedida, EntyEqumedmaunidadmedidaDto> entityToDtoTranslate;
    private final Translator<EntyEqumedmaunidadmedidaDto, EntyEqumedmaunidadmedida> dtoToEntityTranslate;

    /**
     * Consulta todas las unidades de medida registradas.
     *
     * @return lista de unidades de medida.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional(readOnly = true)
    public List<EntyEqumedmaunidadmedidaDto> findAll() throws EBusinessException {
        List<EntyEqumedmaunidadmedidaDto> result = new ArrayList<>();

        for (EntyEqumedmaunidadmedida entity : repository.findAll()) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Guarda una unidad de medida.
     *
     * @param dto información de la unidad de medida.
     * @return unidad de medida guardada.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public EntyEqumedmaunidadmedidaDto save(final EntyEqumedmaunidadmedidaDto dto) throws EBusinessException {
        EntyEqumedmaunidadmedida entity = dtoToEntityTranslate.translate(dto);

        if (entity.getEquEstadoregUnme() == null || entity.getEquEstadoregUnme().isBlank()) {
            entity.setEquEstadoregUnme("1");
        }

        EntyEqumedmaunidadmedida saved = repository.save(entity);
        return entityToDtoTranslate.translate(saved);
    }
}