package com.system.modules.equiposmaquinaria.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyPrvinvmdequipmaquinariaDto;
import com.system.crosscutting.domain.model.EntyPrvinvmdequipmaquinariaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.contracts.IjpaDataProviders;

public interface IjpaTipoEquipoDataProviders
        extends IjpaDataProviders<EntyPrvinvmdequipmaquinariaDto, EntyPrvinvmdequipmaquinariaResponse> {

    EntyPrvinvmdequipmaquinariaDto findByKey(
            String tipoEquipoKey
    ) throws EBusinessException;

    List<EntyPrvinvmdequipmaquinariaDto> findByUnidad(
            String unidadKey
    ) throws EBusinessException;

    List<EntyPrvinvmdequipmaquinariaDto> findByEstado(
            String estado
    ) throws EBusinessException;
}