package com.system.modules.workcontrol.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosDto;
import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.contracts.IjpaDataProviders;

public interface IjpaSitiosPuntosDataProviders
        extends IjpaDataProviders<EntyOrsordmdsitiospuntosDto, EntyOrsordmdsitiospuntosResponse> {

    List<EntyOrsordmdsitiospuntosDto> findByOrden(
            String ordenKey
    ) throws EBusinessException;

    boolean existsByPuntoKey(
            String puntoKey
    ) throws EBusinessException;

    boolean existsActiveByOrden(
            String ordenKey
    ) throws EBusinessException;

    EntyOrsordmdsitiospuntosResponse findByOrdenResponse(
            String ordenKey
    ) throws EBusinessException;
}