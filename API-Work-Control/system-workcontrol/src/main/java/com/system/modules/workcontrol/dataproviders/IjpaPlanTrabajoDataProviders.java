package com.system.modules.workcontrol.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoDto;
import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.contracts.IjpaDataProviders;

public interface IjpaPlanTrabajoDataProviders
        extends IjpaDataProviders<
        EntyOrsplamaplandetrabajoDto,
        EntyOrsplamaplandetrabajoResponse
        > {

    List<EntyOrsplamaplandetrabajoDto> findByOrden(
            String ordenKey
    ) throws EBusinessException;

    List<EntyOrsplamaplandetrabajoDto> findByPunto(
            String puntoKey
    ) throws EBusinessException;

    boolean existsByPlanKey(
            String planKey
    ) throws EBusinessException;

    boolean existsActiveByOrden(
            String ordenKey
    ) throws EBusinessException;

    EntyOrsplamaplandetrabajoResponse findByOrdenResponse(
            String ordenKey
    ) throws EBusinessException;

    EntyOrsplamaplandetrabajoResponse findByPuntoResponse(
            String puntoKey
    ) throws EBusinessException;
}