package com.system.modules.equiposmaquinaria.usecase;

import com.system.crosscutting.domain.model.EntyEqutipmatipoequipoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyEqutipmatipoequipos;
import com.system.crosscutting.persistence.repository.EntyEqutipmatipoequipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Caso de uso para gestionar tipos de equipos y maquinaria.
 */
@Service
@RequiredArgsConstructor
public class EntyTipoEquipoService {
    private final EntyEqutipmatipoequipoRepository repository;
    private final Translator<EntyEqutipmatipoequipos, EntyEqutipmatipoequipoDto> entityToDtoTranslate;
    private final Translator<EntyEqutipmatipoequipoDto, EntyEqutipmatipoequipos> dtoToEntityTranslate;

    /**
     * Consulta todos los tipos de equipos registrados.
     *
     * @return lista de tipos de equipos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyEqutipmatipoequipoDto> findAll() throws EBusinessException {
        List<EntyEqutipmatipoequipoDto> result = new ArrayList<>();

        for (EntyEqutipmatipoequipos entity : repository.findAll()) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Guarda un tipo de equipo.
     *
     * @param dto información del tipo de equipo.
     * @return tipo de equipo guardado.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public EntyEqutipmatipoequipoDto save(final EntyEqutipmatipoequipoDto dto) throws EBusinessException {
        EntyEqutipmatipoequipos entity = dtoToEntityTranslate.translate(dto);

        if (entity.getEquEstadoregTieq() == null || entity.getEquEstadoregTieq().isBlank()) {
            entity.setEquEstadoregTieq("1");
        }

        EntyEqutipmatipoequipos saved = repository.save(entity);
        return entityToDtoTranslate.translate(saved);
    }
}
