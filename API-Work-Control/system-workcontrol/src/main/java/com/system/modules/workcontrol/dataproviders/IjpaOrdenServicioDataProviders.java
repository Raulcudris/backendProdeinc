package com.system.modules.workcontrol.dataproviders;

import java.util.List;
import java.util.Optional;

import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioResponse;
import com.system.modules.workcontrol.contracts.IjpaDataProviders;

public interface IjpaOrdenServicioDataProviders
        extends IjpaDataProviders<
        EntyOrsordmaordenservicioDto,
        EntyOrsordmaordenservicioResponse
        > {

    EntyOrsordmaordenservicioDto create(
            EntyOrsordmaordenservicioDto dto
    );

    Optional<EntyOrsordmaordenservicioDto> getByKey(
            String ordenKey
    );

    List<EntyOrsordmaordenservicioDto> getByEstado(
            String estado
    );

    boolean existsByOrdenKey(
            String ordenKey
    );
}