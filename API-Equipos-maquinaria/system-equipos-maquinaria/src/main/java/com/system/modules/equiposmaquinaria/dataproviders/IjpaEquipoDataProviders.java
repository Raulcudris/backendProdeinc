package com.system.modules.equiposmaquinaria.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyPrvinvmainventarioequiposDto;
import com.system.crosscutting.domain.model.EntyPrvinvmainventarioequiposResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.contracts.IjpaDataProviders;

public interface IjpaEquipoDataProviders
        extends IjpaDataProviders<EntyPrvinvmainventarioequiposDto, EntyPrvinvmainventarioequiposResponse> {

    EntyPrvinvmainventarioequiposDto findByKey(
            String equipoKey
    ) throws EBusinessException;

    List<EntyPrvinvmainventarioequiposDto> findByProveedor(
            String proveedorKey
    ) throws EBusinessException;

    List<EntyPrvinvmainventarioequiposDto> findByTipoEquipo(
            String tipoEquipoKey
    ) throws EBusinessException;

    List<EntyPrvinvmainventarioequiposDto> findByDisponible(
            String disponible
    ) throws EBusinessException;

    List<EntyPrvinvmainventarioequiposDto> findByEstado(
            String estado
    ) throws EBusinessException;
}