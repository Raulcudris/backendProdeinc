package com.system.modules.workcontrol.contracts;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamddetalleequipooperacionDto;
import com.system.crosscutting.domain.model.EntyOrsplamddetalleequipooperacionResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;

public interface IjpaDetalleEquipoOperacionDataProviders extends IjpaDataProviders<
        EntyOrsplamddetalleequipooperacionDto,
        EntyOrsplamddetalleequipooperacionResponse> {

    EntyOrsplamddetalleequipooperacionDto findByKey(String detalleEquipoOperacionKey) throws EBusinessException;

    List<EntyOrsplamddetalleequipooperacionDto> findByReporteOperacion(String reporteOperacionKey) throws EBusinessException;

    List<EntyOrsplamddetalleequipooperacionDto> findByOrden(String ordenKey) throws EBusinessException;

    List<EntyOrsplamddetalleequipooperacionDto> findByProyeccionSemana(String proyeccionSemanaKey) throws EBusinessException;

    List<EntyOrsplamddetalleequipooperacionDto> findByPlanSemanal(String planSemanalKey) throws EBusinessException;

    List<EntyOrsplamddetalleequipooperacionDto> findByPunto(String puntoKey) throws EBusinessException;

    List<EntyOrsplamddetalleequipooperacionDto> findByEquipo(String equipoKey) throws EBusinessException;

    List<EntyOrsplamddetalleequipooperacionDto> findByTipoEquipo(String tipoEquipoKey) throws EBusinessException;

    List<EntyOrsplamddetalleequipooperacionDto> findByFechaTrabajo(LocalDate fechaTrabajo) throws EBusinessException;

    List<EntyOrsplamddetalleequipooperacionDto> findByEstado(String estado) throws EBusinessException;

    BigDecimal sumHorasByPlanSemanal(String planSemanalKey) throws EBusinessException;

    BigDecimal sumValorEjecutadoByPlanSemanal(String planSemanalKey) throws EBusinessException;

    BigDecimal sumHorasByOrden(String ordenKey) throws EBusinessException;

    BigDecimal sumValorEjecutadoByOrden(String ordenKey) throws EBusinessException;

    EntyOrsplamddetalleequipooperacionDto changestatus(Integer id, String status) throws EBusinessException;

}