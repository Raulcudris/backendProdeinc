package com.system.modules.workcontrol.contracts;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsordmdactamodificaciondetalleDto;
import com.system.crosscutting.domain.model.EntyOrsordmdactamodificaciondetalleResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;

public interface IjpaActaModificacionDetalleDataProviders extends IjpaDataProviders<
        EntyOrsordmdactamodificaciondetalleDto,
        EntyOrsordmdactamodificaciondetalleResponse> {

    EntyOrsordmdactamodificaciondetalleDto findByKey(String detalleActaModificacionKey) throws EBusinessException;

    List<EntyOrsordmdactamodificaciondetalleDto> findByActa(String actaModificacionKey) throws EBusinessException;

    List<EntyOrsordmdactamodificaciondetalleDto> findByOrden(String ordenKey) throws EBusinessException;

    List<EntyOrsordmdactamodificaciondetalleDto> findByResumenEquipo(String resumenEquipoKey) throws EBusinessException;

    List<EntyOrsordmdactamodificaciondetalleDto> findByTipoEquipo(String tipoEquipoKey) throws EBusinessException;

    List<EntyOrsordmdactamodificaciondetalleDto> findByEstado(String estado) throws EBusinessException;

    EntyOrsordmdactamodificaciondetalleDto changestatus(Integer id, String status) throws EBusinessException;
}