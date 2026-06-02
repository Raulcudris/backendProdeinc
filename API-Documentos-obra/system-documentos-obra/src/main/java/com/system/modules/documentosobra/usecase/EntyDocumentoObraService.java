package com.system.modules.documentosobra.usecase;
import com.system.crosscutting.domain.model.EntyDocdocmadocumentoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDocdocmadocumento;
import com.system.crosscutting.persistence.repository.EntyDocdocmadocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Caso de uso para gestionar documentos legales, técnicos, contractuales,
 * administrativos y de maquinaria.
 */
@Service
@RequiredArgsConstructor
public class EntyDocumentoObraService {
    private final EntyDocdocmadocumentoRepository repository;
    private final Translator<EntyDocdocmadocumento, EntyDocdocmadocumentoDto> entityToDtoTranslate;
    private final Translator<EntyDocdocmadocumentoDto, EntyDocdocmadocumento> dtoToEntityTranslate;

    /**
     * Consulta todos los documentos registrados.
     *
     * @return lista de documentos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyDocdocmadocumentoDto> findAll() throws EBusinessException {
        List<EntyDocdocmadocumentoDto> result = new ArrayList<>();

        for (EntyDocdocmadocumento entity : repository.findAll()) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta documentos por tipo documental.
     *
     * @param tipoDocumentoKey identificador funcional del tipo documental.
     * @return lista de documentos encontrados.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyDocdocmadocumentoDto> findByTipoDocumento(final String tipoDocumentoKey) throws EBusinessException {
        List<EntyDocdocmadocumentoDto> result = new ArrayList<>();

        for (EntyDocdocmadocumento entity : repository.findByDocIdentifkeyTido(tipoDocumentoKey)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta documentos por referencia.
     *
     * @param tipoReferencia tipo de referencia asociada al documento.
     * @param referenciaId identificador del registro referenciado.
     * @return lista de documentos encontrados.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyDocdocmadocumentoDto> findByReferencia(final String tipoReferencia, final String referenciaId) throws EBusinessException {
        List<EntyDocdocmadocumentoDto> result = new ArrayList<>();

        for (EntyDocdocmadocumento entity : repository.findByDocTiporeferenDocuAndDocReferenciaidDocu(tipoReferencia, referenciaId)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta documentos vencidos o con vencimiento menor o igual a la fecha actual.
     *
     * @return lista de documentos vencidos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyDocdocmadocumentoDto> findVencidos() throws EBusinessException {
        List<EntyDocdocmadocumentoDto> result = new ArrayList<>();

        for (EntyDocdocmadocumento entity : repository.findByDocFechavenceDocuLessThanEqual(LocalDate.now())) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Guarda un documento de obra.
     *
     * @param dto información del documento.
     * @return documento guardado.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public EntyDocdocmadocumentoDto save(final EntyDocdocmadocumentoDto dto) throws EBusinessException {
        EntyDocdocmadocumento entity = dtoToEntityTranslate.translate(dto);

        if (entity.getDocEstadoregDocu() == null || entity.getDocEstadoregDocu().isBlank()) {
            entity.setDocEstadoregDocu("1");
        }

        EntyDocdocmadocumento saved = repository.save(entity);
        return entityToDtoTranslate.translate(saved);
    }
}
