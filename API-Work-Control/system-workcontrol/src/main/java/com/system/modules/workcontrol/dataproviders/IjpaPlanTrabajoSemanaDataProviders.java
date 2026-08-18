package com.system.modules.workcontrol.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.contracts.IjpaDataProviders;

public interface IjpaPlanTrabajoSemanaDataProviders
        extends IjpaDataProviders<
        EntyOrsplamdplantrabsemanaDto,
        EntyOrsplamdplantrabsemanaResponse
        > {

    List<EntyOrsplamdplantrabsemanaDto> findByOrden(
            String ordenKey
    ) throws EBusinessException;

    List<EntyOrsplamdplantrabsemanaDto> findByPlanTrabajo(
            String planTrabajoKey
    ) throws EBusinessException;

    List<EntyOrsplamdplantrabsemanaDto> findBySemana(
            String semanaKey
    ) throws EBusinessException;

    boolean existsByPlanSemanaKey(
            String planSemanaKey
    ) throws EBusinessException;

    boolean existsActiveByOrden(
            String ordenKey
    ) throws EBusinessException;

    EntyOrsplamdplantrabsemanaResponse findByOrdenResponse(
            String ordenKey
    ) throws EBusinessException;

    EntyOrsplamdplantrabsemanaResponse findByPlanTrabajoResponse(
            String planTrabajoKey
    ) throws EBusinessException;

    EntyOrsplamdplantrabsemanaResponse findBySemanaResponse(
            String semanaKey
    ) throws EBusinessException;
}