package com.system.modules.controlobras.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsordmdproyecsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsordmdproyecsemanaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.contracts.IjpaDataProviders;

public interface IjpaProyeccionSemanaDataProviders
        extends IjpaDataProviders<EntyOrsordmdproyecsemanaDto, EntyOrsordmdproyecsemanaResponse> {

    List<EntyOrsordmdproyecsemanaDto> findByOrden(String ordenKey) throws EBusinessException;
}