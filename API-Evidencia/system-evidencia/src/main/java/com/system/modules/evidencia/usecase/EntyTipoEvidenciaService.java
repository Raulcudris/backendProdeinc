package com.system.modules.evidencia.usecase;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.dataproviders.IjpaTipoEvidenciaDataProviders;

@Service
public class EntyTipoEvidenciaService {

    @Autowired
    private IjpaTipoEvidenciaDataProviders dataProviders;

    public EntyEvitipmatipoevidenciaResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyEvitipmatipoevidenciaResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEvitipmatipoevidenciaDto get(
            final Integer id
    ) throws EBusinessException {
        return dataProviders.get(id);
    }

    public EntyEvitipmatipoevidenciaDto saveBefore(
            final EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {
        prepareBeforeSave(dto);

        dto.setEviIdentifkeyTiev(dto.getEviIdentifkeyTiev().trim().toUpperCase());

        return dataProviders.save(dto);
    }

    public List<EntyEvitipmatipoevidenciaDto> saveBefore(
            final List<EntyEvitipmatipoevidenciaDto> dtos
    ) throws EBusinessException {
        if (dtos != null) {
            for (EntyEvitipmatipoevidenciaDto dto : dtos) {
                prepareBeforeSave(dto);
                dto.setEviIdentifkeyTiev(dto.getEviIdentifkeyTiev().trim().toUpperCase());
            }
        }

        return dataProviders.save(dtos);
    }

    public EntyEvitipmatipoevidenciaDto updateBefore(
            final Integer id,
            final EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {
        prepareBeforeSave(dto);

        dto.setEviIdentifkeyTiev(dto.getEviIdentifkeyTiev().trim().toUpperCase());

        return dataProviders.update(id, dto);
    }

    public EntyEvitipmatipoevidenciaDto changestatus(
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

    public EntyEvitipmatipoevidenciaDto findByKey(
            final String tipoEvidenciaKey
    ) throws EBusinessException {
        return dataProviders.findByKey(tipoEvidenciaKey);
    }

    public EntyEvitipmatipoevidenciaResponse findByEstado(
            final String estado
    ) throws EBusinessException {
        return dataProviders.findByEstado(estado);
    }

    private void prepareBeforeSave(
            final EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {
        if (dto == null) {
            throw new EBusinessException("El tipo de evidencia es obligatorio.");
        }

        if (dto.getEviIdentifkeyTiev() == null
                || dto.getEviIdentifkeyTiev().isBlank()) {
            throw new EBusinessException("El código del tipo de evidencia es obligatorio.");
        }

        if (dto.getEviDescripcionTiev() == null
                || dto.getEviDescripcionTiev().isBlank()) {
            throw new EBusinessException("La descripción del tipo de evidencia es obligatoria.");
        }

        if (dto.getEviTiporegistTiev() == null
                || dto.getEviTiporegistTiev().isBlank()) {
            dto.setEviTiporegistTiev("1");
        }

        if (dto.getEviEstadoregTiev() == null
                || dto.getEviEstadoregTiev().isBlank()) {
            dto.setEviEstadoregTiev("1");
        }
    }
}