package com.system.modules.workcontrol.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;

public interface IjpaEvidenciaDataProviders {

    EntyEvievimaevidenciaResponse getAll()
            throws EBusinessException;

    EntyEvievimaevidenciaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException;

    EntyEvievimaevidenciaDto get(Integer id)
            throws EBusinessException;

    EntyEvievimaevidenciaDto findByEvidenciaKey(String evidenciaKey)
            throws EBusinessException;

    EntyEvievimaevidenciaDto save(
            EntyEvievimaevidenciaDto dto
    ) throws EBusinessException;

    List<EntyEvievimaevidenciaDto> save(
            List<EntyEvievimaevidenciaDto> dtos
    ) throws EBusinessException;

    EntyEvievimaevidenciaDto update(
            Integer id,
            EntyEvievimaevidenciaDto dto
    ) throws EBusinessException;

    void delete(Integer id)
            throws EBusinessException;

    boolean existsByEvidenciaKey(String evidenciaKey)
            throws EBusinessException;

    List<EntyEvievimaevidenciaDto> findByTipo(String tipoEvidenciaKey)
            throws EBusinessException;

    List<EntyEvievimaevidenciaDto> findByEstado(String estado)
            throws EBusinessException;

    EntyEvievimaevidenciaResponse findByTipoResponse(String tipoEvidenciaKey)
            throws EBusinessException;

    EntyEvievimaevidenciaResponse findByEstadoResponse(String estado)
            throws EBusinessException;
}