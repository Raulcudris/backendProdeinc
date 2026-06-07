package com.system.modules.equiposmaquinaria.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyPrvinvmdunidamedequipoDto;
import com.system.crosscutting.domain.model.EntyPrvinvmdunidamedequipoResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.contracts.IjpaDataProviders;

public interface IjpaUnidadMedidaDataProviders
        extends IjpaDataProviders<EntyPrvinvmdunidamedequipoDto, EntyPrvinvmdunidamedequipoResponse> {

    EntyPrvinvmdunidamedequipoDto findByKey(
            String unidadKey
    ) throws EBusinessException;

    List<EntyPrvinvmdunidamedequipoDto> findByEstado(
            String estado
    ) throws EBusinessException;
}