package com.system.modules.evidencia.dataproviders;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.contracts.IjpaDataProviders;

public interface IjpaReferenciaEvidenciaDataProviders
        extends IjpaDataProviders<
        EntyEvirefmdreferenciaDto,
        EntyEvirefmdreferenciaResponse> {

    EntyEvirefmdreferenciaDto findByKey(String referenciaKey)
            throws EBusinessException;

    EntyEvirefmdreferenciaResponse findByEvidencia(String evidenciaKey)
            throws EBusinessException;

    EntyEvirefmdreferenciaResponse findByRegistro(String registroKey)
            throws EBusinessException;

    EntyEvirefmdreferenciaResponse findByTipoRegistro(String tipoRegistro)
            throws EBusinessException;

    EntyEvirefmdreferenciaResponse findByTipoRegistroAndRegistro(
            String tipoRegistro,
            String registroKey
    ) throws EBusinessException;

    EntyEvirefmdreferenciaResponse findByEstado(String estado)
            throws EBusinessException;

    EntyEvirefmdreferenciaDto changestatus(Integer id, String estado)
            throws EBusinessException;
}