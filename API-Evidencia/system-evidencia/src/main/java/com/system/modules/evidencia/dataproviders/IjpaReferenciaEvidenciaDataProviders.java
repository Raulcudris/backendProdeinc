package com.system.modules.evidencia.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.contracts.IjpaDataProviders;

public interface IjpaReferenciaEvidenciaDataProviders
        extends IjpaDataProviders<EntyEvirefmdreferenciaDto, EntyEvirefmdreferenciaResponse> {

    List<EntyEvirefmdreferenciaDto> findByEvidencia(
            String evidenciaKey
    ) throws EBusinessException;

    List<EntyEvirefmdreferenciaDto> findByRegistro(
            String registroKey
    ) throws EBusinessException;

    List<EntyEvirefmdreferenciaDto> findByTipoRegistro(
            String tipoRegistro
    ) throws EBusinessException;

    List<EntyEvirefmdreferenciaDto> findByTipoRegistroAndRegistro(
            String tipoRegistro,
            String registroKey
    ) throws EBusinessException;

    List<EntyEvirefmdreferenciaDto> findByEstado(
            String estado
    ) throws EBusinessException;
}