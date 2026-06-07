package com.system.modules.controlobras.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.contracts.IjpaDataProviders;

public interface IjpaPlanTrabajoSemanaDataProviders
        extends IjpaDataProviders<EntyOrsplamdplantrabsemanaDto, EntyOrsplamdplantrabsemanaResponse> {

    List<EntyOrsplamdplantrabsemanaDto> findByOrden(String ordenKey) throws EBusinessException;

    List<EntyOrsplamdplantrabsemanaDto> findByPlan(String planKey) throws EBusinessException;

    List<EntyOrsplamdplantrabsemanaDto> findByProyeccionSemana(String proyeccionKey) throws EBusinessException;
}