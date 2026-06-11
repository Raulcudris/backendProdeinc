package com.system.modules.controlobras.usecase;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsordmdactamodificaciondetalleDto;
import com.system.crosscutting.domain.model.EntyOrsordmdactamodificaciondetalleResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.contracts.IjpaActaModificacionDetalleDataProviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntyActaModificacionDetalleService {

    @Autowired
    private IjpaActaModificacionDetalleDataProviders dataProvider;

    public EntyOrsordmdactamodificaciondetalleResponse getAll()
            throws EBusinessException {
        return dataProvider.getAll();
    }

    public EntyOrsordmdactamodificaciondetalleResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsordmdactamodificaciondetalleDto get(final Integer id)
            throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsordmdactamodificaciondetalleDto saveBefore(
            final EntyOrsordmdactamodificaciondetalleDto dto
    ) throws EBusinessException {
        aplicarDefaults(dto);
        return dataProvider.save(dto);
    }

    public List<EntyOrsordmdactamodificaciondetalleDto> saveBefore(
            final List<EntyOrsordmdactamodificaciondetalleDto> dtoList
    ) throws EBusinessException {
        if (dtoList != null) {
            for (EntyOrsordmdactamodificaciondetalleDto dto : dtoList) {
                aplicarDefaults(dto);
            }
        }

        return dataProvider.save(dtoList);
    }

    public EntyOrsordmdactamodificaciondetalleDto updateBefore(
            final Integer id,
            final EntyOrsordmdactamodificaciondetalleDto dto
    ) throws EBusinessException {
        aplicarDefaults(dto);
        return dataProvider.update(id, dto);
    }

    public EntyOrsordmdactamodificaciondetalleDto changestatus(
            final Integer id,
            final String status
    ) throws EBusinessException {
        return dataProvider.changestatus(id, status);
    }

    public void deleteBefore(final Integer id) throws EBusinessException {
        dataProvider.delete(id);
    }

    public EntyOrsordmdactamodificaciondetalleDto findByKey(
            final String detalleActaModificacionKey
    ) throws EBusinessException {
        return dataProvider.findByKey(detalleActaModificacionKey);
    }

    public List<EntyOrsordmdactamodificaciondetalleDto> findByActa(
            final String actaModificacionKey
    ) throws EBusinessException {
        return dataProvider.findByActa(actaModificacionKey);
    }

    public List<EntyOrsordmdactamodificaciondetalleDto> findByOrden(
            final String ordenKey
    ) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public List<EntyOrsordmdactamodificaciondetalleDto> findByResumenEquipo(
            final String resumenEquipoKey
    ) throws EBusinessException {
        return dataProvider.findByResumenEquipo(resumenEquipoKey);
    }

    public List<EntyOrsordmdactamodificaciondetalleDto> findByTipoEquipo(
            final String tipoEquipoKey
    ) throws EBusinessException {
        return dataProvider.findByTipoEquipo(tipoEquipoKey);
    }

    public List<EntyOrsordmdactamodificaciondetalleDto> findByEstado(
            final String estado
    ) throws EBusinessException {
        return dataProvider.findByEstado(estado);
    }

    private void aplicarDefaults(final EntyOrsordmdactamodificaciondetalleDto dto) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsTiporegistAcmd() == null) {
            dto.setOrsTiporegistAcmd("1");
        }

        if (dto.getOrsEstadoregAcmd() == null) {
            dto.setOrsEstadoregAcmd("1");
        }
    }
}