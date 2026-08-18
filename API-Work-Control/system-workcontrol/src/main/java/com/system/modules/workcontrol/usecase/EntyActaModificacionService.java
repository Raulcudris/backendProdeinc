package com.system.modules.workcontrol.usecase;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsordmaactamodificacionDto;
import com.system.crosscutting.domain.model.EntyOrsordmaactamodificacionResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.contracts.IjpaActaModificacionDataProviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntyActaModificacionService {

    @Autowired
    private IjpaActaModificacionDataProviders dataProvider;

    public EntyOrsordmaactamodificacionResponse getAll() throws EBusinessException {
        return dataProvider.getAll();
    }

    public EntyOrsordmaactamodificacionResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsordmaactamodificacionDto get(final Integer id)
            throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsordmaactamodificacionDto saveBefore(
            final EntyOrsordmaactamodificacionDto dto
    ) throws EBusinessException {
        aplicarDefaults(dto);
        return dataProvider.save(dto);
    }

    public List<EntyOrsordmaactamodificacionDto> saveBefore(
            final List<EntyOrsordmaactamodificacionDto> dtoList
    ) throws EBusinessException {
        if (dtoList != null) {
            for (EntyOrsordmaactamodificacionDto dto : dtoList) {
                aplicarDefaults(dto);
            }
        }

        return dataProvider.save(dtoList);
    }

    public EntyOrsordmaactamodificacionDto updateBefore(
            final Integer id,
            final EntyOrsordmaactamodificacionDto dto
    ) throws EBusinessException {
        aplicarDefaults(dto);
        return dataProvider.update(id, dto);
    }

    public EntyOrsordmaactamodificacionDto changestatus(
            final Integer id,
            final String status
    ) throws EBusinessException {
        return dataProvider.changestatus(id, status);
    }

    public void deleteBefore(final Integer id) throws EBusinessException {
        dataProvider.delete(id);
    }

    public EntyOrsordmaactamodificacionDto findByKey(
            final String actaModificacionKey
    ) throws EBusinessException {
        return dataProvider.findByKey(actaModificacionKey);
    }

    public List<EntyOrsordmaactamodificacionDto> findByOrden(
            final String ordenKey
    ) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public List<EntyOrsordmaactamodificacionDto> findByEstadoActa(
            final String estadoActa
    ) throws EBusinessException {
        return dataProvider.findByEstadoActa(estadoActa);
    }

    public List<EntyOrsordmaactamodificacionDto> findByEstado(
            final String estado
    ) throws EBusinessException {
        return dataProvider.findByEstado(estado);
    }

    private void aplicarDefaults(final EntyOrsordmaactamodificacionDto dto) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsEstadoactaAcmo() == null) {
            dto.setOrsEstadoactaAcmo("BORRADOR");
        }

        if (dto.getOrsTiporegistAcmo() == null) {
            dto.setOrsTiporegistAcmo("1");
        }

        if (dto.getOrsEstadoregAcmo() == null) {
            dto.setOrsEstadoregAcmo("1");
        }
    }
}