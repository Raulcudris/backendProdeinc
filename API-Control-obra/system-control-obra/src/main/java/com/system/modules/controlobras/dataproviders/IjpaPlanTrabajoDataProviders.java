package com.system.modules.controlobras.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoDto;
import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.contracts.IjpaDataProviders;

public interface IjpaPlanTrabajoDataProviders
        extends IjpaDataProviders<EntyOrsplamaplandetrabajoDto, EntyOrsplamaplandetrabajoResponse> {

    List<EntyOrsplamaplandetrabajoDto> findByOrden(String ordenKey) throws EBusinessException;

    List<EntyOrsplamaplandetrabajoDto> findByPunto(String puntoKey) throws EBusinessException;
}