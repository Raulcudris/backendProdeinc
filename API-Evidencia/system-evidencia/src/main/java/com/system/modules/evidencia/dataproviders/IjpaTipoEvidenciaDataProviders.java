package com.system.modules.evidencia.dataproviders;

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.contracts.IjpaDataProviders;

public interface IjpaTipoEvidenciaDataProviders
        extends IjpaDataProviders<
        EntyEvitipmatipoevidenciaDto,
        EntyEvitipmatipoevidenciaResponse> {

    EntyEvitipmatipoevidenciaDto findByKey(String tipoEvidenciaKey)
            throws EBusinessException;

    EntyEvitipmatipoevidenciaResponse findByEstado(String estado)
            throws EBusinessException;

    EntyEvitipmatipoevidenciaDto changestatus(Integer id, String estado)
            throws EBusinessException;
}