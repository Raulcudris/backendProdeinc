package com.system.modules.evidencia.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.contracts.IjpaDataProviders;

public interface IjpaEvidenciaDataProviders
        extends IjpaDataProviders<EntyEvievimaevidenciaDto, EntyEvievimaevidenciaResponse> {

    List<EntyEvievimaevidenciaDto> findByTipo(
            String tipoKey
    ) throws EBusinessException;

    List<EntyEvievimaevidenciaDto> findByEstado(
            String estado
    ) throws EBusinessException;
}