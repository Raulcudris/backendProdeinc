package com.system.modules.evidencia.usecase;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.dataproviders.IjpaEvidenciaDataProviders;

@Service
public class EntyEvidenciaService {

    @Autowired
    private IjpaEvidenciaDataProviders dataProviders;

    public EntyEvievimaevidenciaResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyEvievimaevidenciaResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEvievimaevidenciaDto get(
            final Integer id
    ) throws EBusinessException {
        return dataProviders.get(id);
    }

    public EntyEvievimaevidenciaDto saveBefore(
            final EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {

        prepareBeforeSave(dto);

        return dataProviders.save(dto);
    }

    public List<EntyEvievimaevidenciaDto> saveBefore(
            final List<EntyEvievimaevidenciaDto> dtos
    ) throws EBusinessException {

        if (dtos != null) {
            for (EntyEvievimaevidenciaDto dto : dtos) {
                prepareBeforeSave(dto);
            }
        }

        return dataProviders.save(dtos);
    }

    public EntyEvievimaevidenciaDto updateBefore(
            final Integer id,
            final EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {

        prepareBeforeSave(dto);

        return dataProviders.update(id, dto);
    }

    public EntyEvievimaevidenciaDto changestatus(
            final Integer id,
            final String estado
    ) throws EBusinessException {

        if (!"1".equals(estado) && !"2".equals(estado)) {
            throw new EBusinessException("El estado debe ser 1 activo o 2 inactivo.");
        }

        return dataProviders.changestatus(id, estado);
    }

    public void deleteBefore(
            final Integer id
    ) throws EBusinessException {
        dataProviders.delete(id);
    }

    public EntyEvievimaevidenciaDto findByKey(
            final String evidenciaKey
    ) throws EBusinessException {
        return dataProviders.findByKey(evidenciaKey);
    }

    public EntyEvievimaevidenciaResponse findByTipo(
            final String tipoKey
    ) throws EBusinessException {
        return dataProviders.findByTipo(tipoKey);
    }

    public EntyEvievimaevidenciaResponse findByEstado(
            final String estado
    ) throws EBusinessException {
        return dataProviders.findByEstado(estado);
    }

    private void prepareBeforeSave(
            final EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw new EBusinessException("La evidencia es obligatoria.");
        }

        if (dto.getEviIdentifkeyEvid() == null
                || dto.getEviIdentifkeyEvid().isBlank()) {
            throw new EBusinessException("El código de la evidencia es obligatorio.");
        }

        if (dto.getEviIdentifkeyTiev() == null
                || dto.getEviIdentifkeyTiev().isBlank()) {
            throw new EBusinessException("El tipo de evidencia es obligatorio.");
        }

        if (dto.getEviNombrearchivoEvid() == null
                || dto.getEviNombrearchivoEvid().isBlank()) {
            throw new EBusinessException("El nombre del archivo es obligatorio.");
        }

        if (dto.getEviUrlarchivoEvid() == null
                || dto.getEviUrlarchivoEvid().isBlank()) {
            throw new EBusinessException("La URL del archivo es obligatoria.");
        }

        if (dto.getEviFechacapturaEvid() == null) {
            dto.setEviFechacapturaEvid(LocalDate.now());
        }

        if (dto.getEviTiporegistEvid() == null
                || dto.getEviTiporegistEvid().isBlank()) {
            dto.setEviTiporegistEvid("1");
        }

        if (dto.getEviEstadoregEvid() == null
                || dto.getEviEstadoregEvid().isBlank()) {
            dto.setEviEstadoregEvid("1");
        }
    }
}