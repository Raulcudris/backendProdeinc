package com.system.modules.documentosobra.usecase;

import com.system.crosscutting.domain.model.EntyDocvenmdvencimientoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDocvenmdvencimiento;
import com.system.crosscutting.persistence.repository.EntyDocvenmdvencimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Caso de uso para gestionar vencimientos documentales.
 */
@Service
@RequiredArgsConstructor
public class EntyVencimientoDocumentoService {

    private final EntyDocvenmdvencimientoRepository repository;
    private final Translator<EntyDocvenmdvencimiento, EntyDocvenmdvencimientoDto> entityToDtoTranslate;
    private final Translator<EntyDocvenmdvencimientoDto, EntyDocvenmdvencimiento> dtoToEntityTranslate;

    /**
     * Consulta todos los vencimientos documentales registrados.
     *
     * @return lista de vencimientos documentales.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyDocvenmdvencimientoDto> findAll() throws EBusinessException {
        List<EntyDocvenmdvencimientoDto> result = new ArrayList<>();

        for (EntyDocvenmdvencimiento entity : repository.findAll()) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta vencimientos asociados a un documento.
     *
     * @param documentoKey identificador funcional del documento.
     * @return lista de vencimientos asociados al documento.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyDocvenmdvencimientoDto> findByDocumento(final String documentoKey) throws EBusinessException {
        List<EntyDocvenmdvencimientoDto> result = new ArrayList<>();

        for (EntyDocvenmdvencimiento entity : repository.findByDocIdentifkeyDocu(documentoKey)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta vencimientos vencidos con fecha menor o igual a la fecha actual.
     *
     * @return lista de vencimientos vencidos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyDocvenmdvencimientoDto> findVencidos() throws EBusinessException {
        List<EntyDocvenmdvencimientoDto> result = new ArrayList<>();

        for (EntyDocvenmdvencimiento entity : repository.findByDocFechavenceVedoLessThanEqual(LocalDate.now())) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta vencimientos próximos dentro de un número de días.
     *
     * @param dias número de días para consultar vencimientos próximos.
     * @return lista de vencimientos próximos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyDocvenmdvencimientoDto> findProximos(final int dias) throws EBusinessException {
        List<EntyDocvenmdvencimientoDto> result = new ArrayList<>();

        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusDays(dias);

        for (EntyDocvenmdvencimiento entity : repository.findByDocFechavenceVedoBetween(fechaInicio, fechaFin)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Guarda un vencimiento documental.
     *
     * @param dto información del vencimiento documental.
     * @return vencimiento documental guardado.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public EntyDocvenmdvencimientoDto save(final EntyDocvenmdvencimientoDto dto) throws EBusinessException {
        EntyDocvenmdvencimiento entity = dtoToEntityTranslate.translate(dto);

        if (entity.getDocDiasalertaVedo() == null) {
            entity.setDocDiasalertaVedo(30);
        }

        if (entity.getDocEstadovencVedo() == null || entity.getDocEstadovencVedo().isBlank()) {
            entity.setDocEstadovencVedo("1");
        }

        if (entity.getDocEstadoregVedo() == null || entity.getDocEstadoregVedo().isBlank()) {
            entity.setDocEstadoregVedo("1");
        }

        EntyDocvenmdvencimiento saved = repository.save(entity);
        return entityToDtoTranslate.translate(saved);
    }
}