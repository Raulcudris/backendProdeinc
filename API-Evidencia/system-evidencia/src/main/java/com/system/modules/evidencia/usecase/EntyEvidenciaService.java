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
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEvievimaevidenciaDto get(
            Integer id
    ) throws EBusinessException {
        return dataProviders.get(id);
    }

    public EntyEvievimaevidenciaDto saveBefore(
            EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {

        if (dto.getEviFechacapturaEvid() == null) {
            dto.setEviFechacapturaEvid(LocalDate.now());
        }

        if (dto.getEviTiporegistEvid() == null || dto.getEviTiporegistEvid().isBlank()) {
            dto.setEviTiporegistEvid("1");
        }

        if (dto.getEviEstadoregEvid() == null || dto.getEviEstadoregEvid().isBlank()) {
            dto.setEviEstadoregEvid("1");
        }

        return dataProviders.save(dto);
    }

    public List<EntyEvievimaevidenciaDto> saveBefore(
            List<EntyEvievimaevidenciaDto> dtos
    ) throws EBusinessException {
        return dataProviders.save(dtos);
    }

    public EntyEvievimaevidenciaDto updateBefore(
            Integer id,
            EntyEvievimaevidenciaDto dto
    ) throws EBusinessException {
        return dataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyEvievimaevidenciaDto dto = dataProviders.get(id);

        if (dto == null || dto.getEviPrimarykeyEvid() == null) {
            return "No existe la evidencia con id: " + id;
        }

        dto.setEviEstadoregEvid(estado);

        dataProviders.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(
            Integer id
    ) throws EBusinessException {
        dataProviders.delete(id);
        return "Registro eliminado correctamente";
    }

    public List<EntyEvievimaevidenciaDto> findByTipo(
            String tipoKey
    ) throws EBusinessException {
        return dataProviders.findByTipo(tipoKey);
    }

    public List<EntyEvievimaevidenciaDto> findByEstado(
            String estado
    ) throws EBusinessException {
        return dataProviders.findByEstado(estado);
    }
}