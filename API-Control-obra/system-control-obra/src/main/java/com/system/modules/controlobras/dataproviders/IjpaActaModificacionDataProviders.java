package com.system.modules.controlobras.contracts;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsordmaactamodificacionDto;
import com.system.crosscutting.domain.model.EntyOrsordmaactamodificacionResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;

public interface IjpaActaModificacionDataProviders extends IjpaDataProviders<
        EntyOrsordmaactamodificacionDto,
        EntyOrsordmaactamodificacionResponse> {

    EntyOrsordmaactamodificacionDto findByKey(String actaModificacionKey) throws EBusinessException;

    List<EntyOrsordmaactamodificacionDto> findByOrden(String ordenKey) throws EBusinessException;

    List<EntyOrsordmaactamodificacionDto> findByEstadoActa(String estadoActa) throws EBusinessException;

    List<EntyOrsordmaactamodificacionDto> findByEstado(String estado) throws EBusinessException;

    EntyOrsordmaactamodificacionDto changestatus(Integer id, String status) throws EBusinessException;
}