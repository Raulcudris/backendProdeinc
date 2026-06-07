package com.system.modules.equiposmaquinaria.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyPrvinvmdequipmaquinariaDto;
import com.system.crosscutting.domain.model.EntyPrvinvmdequipmaquinariaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.dataproviders.IjpaTipoEquipoDataProviders;

@Service
public class EntyTipoEquipoService {

    @Autowired
    private IjpaTipoEquipoDataProviders dataProviders;

    public EntyPrvinvmdequipmaquinariaResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyPrvinvmdequipmaquinariaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyPrvinvmdequipmaquinariaDto get(
            Integer id
    ) throws EBusinessException {
        return dataProviders.get(id);
    }

    public EntyPrvinvmdequipmaquinariaDto saveBefore(
            EntyPrvinvmdequipmaquinariaDto dto
    ) throws EBusinessException {

        if (dto.getPrvTiporegistTieq() == null || dto.getPrvTiporegistTieq().isBlank()) {
            dto.setPrvTiporegistTieq("1");
        }

        if (dto.getPrvEstadoregTieq() == null || dto.getPrvEstadoregTieq().isBlank()) {
            dto.setPrvEstadoregTieq("1");
        }

        return dataProviders.save(dto);
    }

    public List<EntyPrvinvmdequipmaquinariaDto> saveBefore(
            List<EntyPrvinvmdequipmaquinariaDto> dtos
    ) throws EBusinessException {
        return dataProviders.save(dtos);
    }

    public EntyPrvinvmdequipmaquinariaDto updateBefore(
            Integer id,
            EntyPrvinvmdequipmaquinariaDto dto
    ) throws EBusinessException {

        if (dto.getPrvTiporegistTieq() == null || dto.getPrvTiporegistTieq().isBlank()) {
            dto.setPrvTiporegistTieq("1");
        }

        if (dto.getPrvEstadoregTieq() == null || dto.getPrvEstadoregTieq().isBlank()) {
            dto.setPrvEstadoregTieq("1");
        }

        return dataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyPrvinvmdequipmaquinariaDto dto = dataProviders.get(id);

        if (dto == null || dto.getPrvPrimarykeyTieq() == null) {
            return "No existe el tipo de equipo con id: " + id;
        }

        dto.setPrvEstadoregTieq(estado);

        dataProviders.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(
            Integer id
    ) throws EBusinessException {
        dataProviders.delete(id);
        return "Registro eliminado correctamente";
    }

    public EntyPrvinvmdequipmaquinariaDto findByKey(
            String tipoEquipoKey
    ) throws EBusinessException {
        return dataProviders.findByKey(tipoEquipoKey);
    }

    public List<EntyPrvinvmdequipmaquinariaDto> findByUnidad(
            String unidadKey
    ) throws EBusinessException {
        return dataProviders.findByUnidad(unidadKey);
    }

    public List<EntyPrvinvmdequipmaquinariaDto> findByEstado(
            String estado
    ) throws EBusinessException {
        return dataProviders.findByEstado(estado);
    }
}