package com.system.modules.controlobras.usecase;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyOrsordmaordenservicio;
import com.system.crosscutting.persistence.repository.EntyOrsordmaordenservicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * Caso de uso para gestionar órdenes de servicio.
 */
@Service
@RequiredArgsConstructor
public class OrdenServicioService {

    private final EntyOrsordmaordenservicioRepository repository;
    private final Translator<EntyOrsordmaordenservicio, EntyOrsordmaordenservicioDto> entityToDtoTranslate;
    private final Translator<EntyOrsordmaordenservicioDto, EntyOrsordmaordenservicio> dtoToEntityTranslate;

    /**
     * Consulta todas las órdenes de servicio registradas.
     *
     * @return lista de órdenes de servicio.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional(readOnly = true)
    public List<EntyOrsordmaordenservicioDto> findAll() throws EBusinessException {
        return repository.findAll()
                .stream()
                .map(entity -> {
                    try {
                        return entityToDtoTranslate.translate(entity);
                    } catch (EBusinessException exception) {
                        throw new RuntimeException(exception);
                    }
                })
                .toList();
    }

    /**
     * Guarda una orden de servicio.
     *
     * @param dto información de la orden de servicio.
     * @return orden de servicio guardada.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public EntyOrsordmaordenservicioDto save(final EntyOrsordmaordenservicioDto dto) throws EBusinessException {
        EntyOrsordmaordenservicio entity = dtoToEntityTranslate.translate(dto);
        EntyOrsordmaordenservicio saved = repository.save(entity);
        return entityToDtoTranslate.translate(saved);
    }
}