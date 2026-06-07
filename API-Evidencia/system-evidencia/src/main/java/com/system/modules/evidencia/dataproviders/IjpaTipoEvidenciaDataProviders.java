package com.system.modules.evidencia.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.contracts.IjpaDataProviders;

public interface IjpaTipoEvidenciaDataProviders
        extends IjpaDataProviders<EntyEvitipmatipoevidenciaDto, EntyEvitipmatipoevidenciaResponse> {

    List<EntyEvitipmatipoevidenciaDto> findByEstado(
            String estado
    ) throws EBusinessException;
}