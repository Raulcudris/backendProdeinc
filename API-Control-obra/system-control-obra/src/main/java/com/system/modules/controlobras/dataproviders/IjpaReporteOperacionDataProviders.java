package com.system.modules.controlobras.contracts;

import java.time.LocalDate;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamdreporteoperacionDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreporteoperacionResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;

public interface IjpaReporteOperacionDataProviders extends IjpaDataProviders<
        EntyOrsplamdreporteoperacionDto,
        EntyOrsplamdreporteoperacionResponse> {

    EntyOrsplamdreporteoperacionDto findByKey(String reporteOperacionKey) throws EBusinessException;

    List<EntyOrsplamdreporteoperacionDto> findByOrden(String ordenKey) throws EBusinessException;

    List<EntyOrsplamdreporteoperacionDto> findByProyeccionSemana(String proyeccionSemanaKey) throws EBusinessException;

    List<EntyOrsplamdreporteoperacionDto> findByPlanSemanal(String planSemanalKey) throws EBusinessException;

    List<EntyOrsplamdreporteoperacionDto> findByPunto(String puntoKey) throws EBusinessException;

    List<EntyOrsplamdreporteoperacionDto> findByProveedor(String proveedorKey) throws EBusinessException;

    List<EntyOrsplamdreporteoperacionDto> findByFechaReporte(LocalDate fechaReporte) throws EBusinessException;

    List<EntyOrsplamdreporteoperacionDto> findByEstado(String estado) throws EBusinessException;

    EntyOrsplamdreporteoperacionDto changestatus(Integer id, String status) throws EBusinessException;
}