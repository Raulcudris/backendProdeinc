package com.system.modules.controlobras.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsordmdresumenequiposDto;
import com.system.crosscutting.domain.model.EntyOrsordmdresumenequiposResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.contracts.IjpaDataProviders;

public interface IjpaResumenEquiposDataProviders
        extends IjpaDataProviders<EntyOrsordmdresumenequiposDto, EntyOrsordmdresumenequiposResponse> {

    List<EntyOrsordmdresumenequiposDto> findByOrden(String ordenKey) throws EBusinessException;
}