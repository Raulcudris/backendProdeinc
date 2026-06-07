package com.system.modules.controlobras.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.contracts.IjpaDataProviders;

public interface IjpaReporteDiarioDataProviders
        extends IjpaDataProviders<EntyOrsplamdreportediarioDto, EntyOrsplamdreportediarioResponse> {

    List<EntyOrsplamdreportediarioDto> findByOrden(String ordenKey) throws EBusinessException;

    List<EntyOrsplamdreportediarioDto> findByPlanSemana(String planSemanaKey) throws EBusinessException;

    List<EntyOrsplamdreportediarioDto> findByProyeccionSemana(String proyeccionKey) throws EBusinessException;
}