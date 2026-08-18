package com.system.modules.workcontrol.usecase;

import java.time.LocalDate;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamdreporteoperacionDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreporteoperacionResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.contracts.IjpaReporteOperacionDataProviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntyReporteOperacionService {

    @Autowired
    private IjpaReporteOperacionDataProviders dataProvider;

    public EntyOrsplamdreporteoperacionResponse getAll() throws EBusinessException {
        return dataProvider.getAll();
    }

    public EntyOrsplamdreporteoperacionResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsplamdreporteoperacionDto get(final Integer id) throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsplamdreporteoperacionDto saveBefore(
            final EntyOrsplamdreporteoperacionDto dto
    ) throws EBusinessException {
        aplicarDefaults(dto);
        return dataProvider.save(dto);
    }

    public List<EntyOrsplamdreporteoperacionDto> saveBefore(
            final List<EntyOrsplamdreporteoperacionDto> dtoList
    ) throws EBusinessException {
        if (dtoList != null) {
            for (EntyOrsplamdreporteoperacionDto dto : dtoList) {
                aplicarDefaults(dto);
            }
        }

        return dataProvider.save(dtoList);
    }

    public EntyOrsplamdreporteoperacionDto updateBefore(
            final Integer id,
            final EntyOrsplamdreporteoperacionDto dto
    ) throws EBusinessException {
        aplicarDefaults(dto);
        return dataProvider.update(id, dto);
    }

    public EntyOrsplamdreporteoperacionDto changestatus(
            final Integer id,
            final String status
    ) throws EBusinessException {
        return dataProvider.changestatus(id, status);
    }

    public void deleteBefore(final Integer id) throws EBusinessException {
        dataProvider.delete(id);
    }

    public EntyOrsplamdreporteoperacionDto findByKey(
            final String reporteOperacionKey
    ) throws EBusinessException {
        return dataProvider.findByKey(reporteOperacionKey);
    }

    public List<EntyOrsplamdreporteoperacionDto> findByOrden(
            final String ordenKey
    ) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public List<EntyOrsplamdreporteoperacionDto> findByProyeccionSemana(
            final String proyeccionSemanaKey
    ) throws EBusinessException {
        return dataProvider.findByProyeccionSemana(proyeccionSemanaKey);
    }

    public List<EntyOrsplamdreporteoperacionDto> findByPlanSemanal(
            final String planSemanalKey
    ) throws EBusinessException {
        return dataProvider.findByPlanSemanal(planSemanalKey);
    }

    public List<EntyOrsplamdreporteoperacionDto> findByPunto(
            final String puntoKey
    ) throws EBusinessException {
        return dataProvider.findByPunto(puntoKey);
    }

    public List<EntyOrsplamdreporteoperacionDto> findByProveedor(
            final String proveedorKey
    ) throws EBusinessException {
        return dataProvider.findByProveedor(proveedorKey);
    }

    public List<EntyOrsplamdreporteoperacionDto> findByFechaReporte(
            final LocalDate fechaReporte
    ) throws EBusinessException {
        return dataProvider.findByFechaReporte(fechaReporte);
    }

    public List<EntyOrsplamdreporteoperacionDto> findByEstado(
            final String estado
    ) throws EBusinessException {
        return dataProvider.findByEstado(estado);
    }

    private void aplicarDefaults(final EntyOrsplamdreporteoperacionDto dto) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsTiporegistRope() == null) {
            dto.setOrsTiporegistRope("1");
        }

        if (dto.getOrsEstadoregRope() == null) {
            dto.setOrsEstadoregRope("1");
        }

        if (dto.getOrsFirmasuministroRope() == null) {
            dto.setOrsFirmasuministroRope("0");
        }

        if (dto.getOrsFirmaseguimientoRope() == null) {
            dto.setOrsFirmaseguimientoRope("0");
        }
    }
}