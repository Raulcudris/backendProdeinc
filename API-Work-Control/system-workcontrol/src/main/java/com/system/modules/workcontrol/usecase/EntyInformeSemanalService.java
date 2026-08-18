package com.system.modules.workcontrol.usecase;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamainformesemanalDto;
import com.system.crosscutting.domain.model.EntyOrsplamainformesemanalResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.contracts.IjpaInformeSemanalDataProviders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntyInformeSemanalService {

    @Autowired
    private IjpaInformeSemanalDataProviders dataProvider;

    public EntyOrsplamainformesemanalResponse getAll() throws EBusinessException {
        return dataProvider.getAll();
    }

    public EntyOrsplamainformesemanalResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsplamainformesemanalDto get(final Integer id) throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsplamainformesemanalDto saveBefore(
            final EntyOrsplamainformesemanalDto dto
    ) throws EBusinessException {
        aplicarDefaults(dto);
        return dataProvider.save(dto);
    }

    public List<EntyOrsplamainformesemanalDto> saveBefore(
            final List<EntyOrsplamainformesemanalDto> dtoList
    ) throws EBusinessException {
        if (dtoList != null) {
            for (EntyOrsplamainformesemanalDto dto : dtoList) {
                aplicarDefaults(dto);
            }
        }

        return dataProvider.save(dtoList);
    }

    public EntyOrsplamainformesemanalDto updateBefore(
            final Integer id,
            final EntyOrsplamainformesemanalDto dto
    ) throws EBusinessException {
        aplicarDefaults(dto);
        return dataProvider.update(id, dto);
    }

    public EntyOrsplamainformesemanalDto changestatus(
            final Integer id,
            final String status
    ) throws EBusinessException {
        return dataProvider.changestatus(id, status);
    }

    public void deleteBefore(final Integer id) throws EBusinessException {
        dataProvider.delete(id);
    }

    public EntyOrsplamainformesemanalDto findByKey(
            final String informeSemanalKey
    ) throws EBusinessException {
        return dataProvider.findByKey(informeSemanalKey);
    }

    public List<EntyOrsplamainformesemanalDto> findByOrden(
            final String ordenKey
    ) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public List<EntyOrsplamainformesemanalDto> findByProyeccionSemana(
            final String proyeccionSemanaKey
    ) throws EBusinessException {
        return dataProvider.findByProyeccionSemana(proyeccionSemanaKey);
    }

    public List<EntyOrsplamainformesemanalDto> findBySemana(
            final Integer semana
    ) throws EBusinessException {
        return dataProvider.findBySemana(semana);
    }

    public List<EntyOrsplamainformesemanalDto> findByEstado(
            final String estado
    ) throws EBusinessException {
        return dataProvider.findByEstado(estado);
    }

    private void aplicarDefaults(final EntyOrsplamainformesemanalDto dto) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsEstadoavanceInse() == null) {
            dto.setOrsEstadoavanceInse("SIN_AVANCE");
        }

        if (dto.getOrsTiporegistInse() == null) {
            dto.setOrsTiporegistInse("1");
        }

        if (dto.getOrsEstadoregInse() == null) {
            dto.setOrsEstadoregInse("1");
        }
    }
}