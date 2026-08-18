package com.system.modules.workcontrol.dataproviders;

import java.time.LocalDate;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.contracts.IjpaDataProviders;

public interface IjpaReporteDiarioDataProviders
        extends IjpaDataProviders<
        EntyOrsplamdreportediarioDto,
        EntyOrsplamdreportediarioResponse
        > {

    List<EntyOrsplamdreportediarioDto> findByOrden(
            String ordenKey
    ) throws EBusinessException;

    List<EntyOrsplamdreportediarioDto> findByPlanSemana(
            String planSemanaKey
    ) throws EBusinessException;

    List<EntyOrsplamdreportediarioDto> findBySemana(
            String semanaKey
    ) throws EBusinessException;

    boolean existsByReporteKey(
            String reporteKey
    ) throws EBusinessException;

    boolean existsReporteValidoByPlanSemanaAndFecha(
            String planSemanaKey,
            LocalDate fecha
    ) throws EBusinessException;

    Long sumEjecutadoValidoByPlanSemana(
            String planSemanaKey
    ) throws EBusinessException;

    EntyOrsplamdreportediarioResponse findByOrdenResponse(
            String ordenKey
    ) throws EBusinessException;

    EntyOrsplamdreportediarioResponse findByPlanSemanaResponse(
            String planSemanaKey
    ) throws EBusinessException;

    EntyOrsplamdreportediarioResponse findBySemanaResponse(
            String semanaKey
    ) throws EBusinessException;
}