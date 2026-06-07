package com.system.modules.controlobras.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosDto;
import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.contracts.IjpaDataProviders;

public interface IjpaSitiosPuntosDataProviders
        extends IjpaDataProviders<EntyOrsordmdsitiospuntosDto, EntyOrsordmdsitiospuntosResponse> {

    List<EntyOrsordmdsitiospuntosDto> findByOrden(String ordenKey) throws EBusinessException;
}