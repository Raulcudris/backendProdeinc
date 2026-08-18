package com.system.modules.workcontrol.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsconfnovedadtiposDto;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadtiposResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.contracts.IjpaDataProviders;

public interface IjpaTipoNovedadDataProviders
        extends IjpaDataProviders<
        EntyOrsconfnovedadtiposDto,
        EntyOrsconfnovedadtiposResponse
        > {

    boolean existsByTipoNovedad(
            String tipoNovedad
    ) throws EBusinessException;

    List<EntyOrsconfnovedadtiposDto> findByEstado(
            String estado
    ) throws EBusinessException;

    EntyOrsconfnovedadtiposResponse findByEstadoResponse(
            String estado
    ) throws EBusinessException;
}