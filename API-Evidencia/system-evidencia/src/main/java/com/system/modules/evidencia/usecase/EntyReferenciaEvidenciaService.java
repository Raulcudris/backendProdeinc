package com.system.modules.evidencia.usecase;

import java.util.List;

import com.system.crosscutting.domain.constants.TipoRegistroEvidenciaConstants;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.dataproviders.IjpaReferenciaEvidenciaDataProviders;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntyReferenciaEvidenciaService {

    private final IjpaReferenciaEvidenciaDataProviders dataProvider;

    public EntyEvirefmdreferenciaResponse getAll()
            throws EBusinessException {
        return dataProvider.getAll();
    }

    public EntyEvirefmdreferenciaResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEvirefmdreferenciaDto get(final Integer id)
            throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyEvirefmdreferenciaDto saveBefore(
            final EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {
        prepareBeforeSave(dto);
        return dataProvider.save(dto);
    }

    public List<EntyEvirefmdreferenciaDto> saveBefore(
            final List<EntyEvirefmdreferenciaDto> dto
    ) throws EBusinessException {
        if (dto == null || dto.isEmpty()) {
            throw new EBusinessException(
                    "La lista de referencias de evidencia es obligatoria."
            );
        }

        for (EntyEvirefmdreferenciaDto item : dto) {
            prepareBeforeSave(item);
        }

        return dataProvider.save(dto);
    }

    public EntyEvirefmdreferenciaDto updateBefore(
            final Integer id,
            final EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {
        prepareBeforeSave(dto);
        return dataProvider.update(id, dto);
    }

    public EntyEvirefmdreferenciaDto changestatus(
            final Integer id,
            final String estado
    ) throws EBusinessException {
        if (!"1".equals(estado) && !"2".equals(estado)) {
            throw new EBusinessException(
                    "El estado debe ser 1 activo o 2 inactivo."
            );
        }

        return dataProvider.changestatus(id, estado);
    }

    public void deleteBefore(final Integer id)
            throws EBusinessException {
        dataProvider.delete(id);
    }

    public EntyEvirefmdreferenciaDto findByKey(
            final String referenciaKey
    ) throws EBusinessException {
        return dataProvider.findByKey(referenciaKey);
    }

    public EntyEvirefmdreferenciaResponse findByEvidencia(
            final String evidenciaKey
    ) throws EBusinessException {
        return dataProvider.findByEvidencia(evidenciaKey);
    }

    public EntyEvirefmdreferenciaResponse findByRegistro(
            final String registroKey
    ) throws EBusinessException {
        return dataProvider.findByRegistro(registroKey);
    }

    public EntyEvirefmdreferenciaResponse findByTipoRegistro(
            final String tipoRegistro
    ) throws EBusinessException {
        validateTipoRegistro(tipoRegistro);

        return dataProvider.findByTipoRegistro(
                TipoRegistroEvidenciaConstants.normalize(tipoRegistro)
        );
    }

    public EntyEvirefmdreferenciaResponse findByTipoRegistroAndRegistro(
            final String tipoRegistro,
            final String registroKey
    ) throws EBusinessException {
        validateTipoRegistro(tipoRegistro);

        if (registroKey == null || registroKey.isBlank()) {
            throw new EBusinessException("El registro asociado es obligatorio.");
        }

        return dataProvider.findByTipoRegistroAndRegistro(
                TipoRegistroEvidenciaConstants.normalize(tipoRegistro),
                registroKey
        );
    }

    public EntyEvirefmdreferenciaResponse findByEstado(
            final String estado
    ) throws EBusinessException {
        return dataProvider.findByEstado(estado);
    }

    private void prepareBeforeSave(final EntyEvirefmdreferenciaDto dto)
            throws EBusinessException {
        validateBeforeSave(dto);

        dto.setEviTiporegistroRefe(
                TipoRegistroEvidenciaConstants.normalize(
                        dto.getEviTiporegistroRefe()
                )
        );

        if (dto.getEviTiporegistRefe() == null
                || dto.getEviTiporegistRefe().isBlank()) {
            dto.setEviTiporegistRefe("1");
        }

        if (dto.getEviEstadoregRefe() == null
                || dto.getEviEstadoregRefe().isBlank()) {
            dto.setEviEstadoregRefe("1");
        }
    }

    private void validateBeforeSave(final EntyEvirefmdreferenciaDto dto)
            throws EBusinessException {

        if (dto == null) {
            throw new EBusinessException(
                    "La referencia de evidencia es obligatoria."
            );
        }

        if (dto.getEviIdentifkeyEvid() == null
                || dto.getEviIdentifkeyEvid().isBlank()) {
            throw new EBusinessException("La evidencia es obligatoria.");
        }

        validateTipoRegistro(dto.getEviTiporegistroRefe());

        if (dto.getEviIdentifregistroRefe() == null
                || dto.getEviIdentifregistroRefe().isBlank()) {
            throw new EBusinessException("El registro asociado es obligatorio.");
        }
    }

    private void validateTipoRegistro(final String tipoRegistro)
            throws EBusinessException {

        if (tipoRegistro == null || tipoRegistro.isBlank()) {
            throw new EBusinessException("El tipo de registro es obligatorio.");
        }

        if (!TipoRegistroEvidenciaConstants.isValid(tipoRegistro)) {
            throw new EBusinessException(
                    "El tipo de registro de evidencia no es válido."
            );
        }
    }
}