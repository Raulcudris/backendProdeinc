package com.system.modules.documentosobra.usecase;
import com.system.crosscutting.domain.model.EntyDoctipmatipodocumentoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.patterns.Translator;
import com.system.crosscutting.persistence.entity.EntyDoctipmatipodocumento;
import com.system.crosscutting.persistence.repository.EntyDoctipmatipodocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Caso de uso para gestionar tipos de documentos.
 */
@Service
@RequiredArgsConstructor
public class EntyTipoDocumentoService {
    private final EntyDoctipmatipodocumentoRepository repository;
    private final Translator<EntyDoctipmatipodocumento, EntyDoctipmatipodocumentoDto> entityToDtoTranslate;
    private final Translator<EntyDoctipmatipodocumentoDto, EntyDoctipmatipodocumento> dtoToEntityTranslate;

    /**
     * Consulta todos los tipos de documentos registrados.
     *
     * @return lista de tipos de documentos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyDoctipmatipodocumentoDto> findAll() throws EBusinessException {
        List<EntyDoctipmatipodocumentoDto> result = new ArrayList<>();

        for (EntyDoctipmatipodocumento entity : repository.findAll()) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta tipos de documento por categoría documental.
     *
     * @param categoriaKey identificador funcional de la categoría documental.
     * @return lista de tipos de documentos encontrados.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyDoctipmatipodocumentoDto> findByCategoria(final String categoriaKey) throws EBusinessException {
        List<EntyDoctipmatipodocumentoDto> result = new ArrayList<>();

        for (EntyDoctipmatipodocumento entity : repository.findByDocIdentifkeyCado(categoriaKey)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Consulta tipos de documento según si requieren vencimiento.
     *
     * @param requiereVencimiento indicador de vencimiento: 1=Sí, 2=No.
     * @return lista de tipos de documentos encontrados.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public List<EntyDoctipmatipodocumentoDto> findByRequiereVencimiento(final String requiereVencimiento) throws EBusinessException {
        List<EntyDoctipmatipodocumentoDto> result = new ArrayList<>();

        for (EntyDoctipmatipodocumento entity : repository.findByDocRequievenceTido(requiereVencimiento)) {
            result.add(entityToDtoTranslate.translate(entity));
        }

        return result;
    }

    /**
     * Guarda un tipo de documento.
     *
     * @param dto información del tipo de documento.
     * @return tipo de documento guardado.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @Transactional
    public EntyDoctipmatipodocumentoDto save(final EntyDoctipmatipodocumentoDto dto) throws EBusinessException {
        EntyDoctipmatipodocumento entity = dtoToEntityTranslate.translate(dto);

        if (entity.getDocRequievenceTido() == null || entity.getDocRequievenceTido().isBlank()) {
            entity.setDocRequievenceTido("2");
        }

        if (entity.getDocEstadoregTido() == null || entity.getDocEstadoregTido().isBlank()) {
            entity.setDocEstadoregTido("1");
        }

        EntyDoctipmatipodocumento saved = repository.save(entity);
        return entityToDtoTranslate.translate(saved);
    }
}
