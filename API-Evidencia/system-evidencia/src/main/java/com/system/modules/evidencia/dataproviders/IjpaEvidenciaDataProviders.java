package com.system.modules.evidencia.dataproviders;

import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.contracts.IjpaDataProviders;

public interface IjpaEvidenciaDataProviders
        extends IjpaDataProviders<
        EntyEvievimaevidenciaDto,
        EntyEvievimaevidenciaResponse> {

    EntyEvievimaevidenciaDto findByKey(String evidenciaKey)
            throws EBusinessException;

    EntyEvievimaevidenciaResponse findByTipo(String tipoKey)
            throws EBusinessException;

    EntyEvievimaevidenciaResponse findByEstado(String estado)
            throws EBusinessException;

    EntyEvievimaevidenciaDto changestatus(Integer id, String estado)
            throws EBusinessException;
}