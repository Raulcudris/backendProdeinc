package com.system.modules.equiposmaquinaria.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyPrvinvmainventarioequiposDto;
import com.system.crosscutting.domain.model.EntyPrvinvmainventarioequiposResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.dataproviders.IjpaEquipoDataProviders;

@Service
public class EntyEquipoService {

    @Autowired
    private IjpaEquipoDataProviders dataProviders;

    public EntyPrvinvmainventarioequiposResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyPrvinvmainventarioequiposResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyPrvinvmainventarioequiposDto get(
            Integer id
    ) throws EBusinessException {
        return dataProviders.get(id);
    }

    public EntyPrvinvmainventarioequiposDto saveBefore(
            EntyPrvinvmainventarioequiposDto dto
    ) throws EBusinessException {

        if (dto.getPrvEquipoestadoInve() == null || dto.getPrvEquipoestadoInve().isBlank()) {
            dto.setPrvEquipoestadoInve("A01");
        }

        if (dto.getPrvEquipoactivoInve() == null || dto.getPrvEquipoactivoInve().isBlank()) {
            dto.setPrvEquipoactivoInve("1");
        }

        if (dto.getPrvEstadoregInve() == null || dto.getPrvEstadoregInve().isBlank()) {
            dto.setPrvEstadoregInve("1");
        }

        return dataProviders.save(dto);
    }

    public List<EntyPrvinvmainventarioequiposDto> saveBefore(
            List<EntyPrvinvmainventarioequiposDto> dtos
    ) throws EBusinessException {
        return dataProviders.save(dtos);
    }

    public EntyPrvinvmainventarioequiposDto updateBefore(
            Integer id,
            EntyPrvinvmainventarioequiposDto dto
    ) throws EBusinessException {

        if (dto.getPrvEquipoestadoInve() == null || dto.getPrvEquipoestadoInve().isBlank()) {
            dto.setPrvEquipoestadoInve("A01");
        }

        if (dto.getPrvEquipoactivoInve() == null || dto.getPrvEquipoactivoInve().isBlank()) {
            dto.setPrvEquipoactivoInve("1");
        }

        if (dto.getPrvEstadoregInve() == null || dto.getPrvEstadoregInve().isBlank()) {
            dto.setPrvEstadoregInve("1");
        }

        return dataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyPrvinvmainventarioequiposDto dto = dataProviders.get(id);

        if (dto == null || dto.getPrvPrimarykeyInve() == null) {
            return "No existe el equipo con id: " + id;
        }

        dto.setPrvEstadoregInve(estado);

        dataProviders.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String cambiarDisponibilidad(
            Integer id,
            String disponible
    ) throws EBusinessException {

        EntyPrvinvmainventarioequiposDto dto = dataProviders.get(id);

        if (dto == null || dto.getPrvPrimarykeyInve() == null) {
            return "No existe el equipo con id: " + id;
        }

        dto.setPrvEquipoactivoInve(disponible);

        dataProviders.update(id, dto);

        return "Disponibilidad actualizada correctamente";
    }

    public String cambiarEstadoOperativo(
            Integer id,
            String estadoOperativo
    ) throws EBusinessException {

        EntyPrvinvmainventarioequiposDto dto = dataProviders.get(id);

        if (dto == null || dto.getPrvPrimarykeyInve() == null) {
            return "No existe el equipo con id: " + id;
        }

        dto.setPrvEquipoestadoInve(estadoOperativo);

        dataProviders.update(id, dto);

        return "Estado operativo actualizado correctamente";
    }

    public String deleteBefore(
            Integer id
    ) throws EBusinessException {
        dataProviders.delete(id);
        return "Registro eliminado correctamente";
    }

    public EntyPrvinvmainventarioequiposDto findByKey(
            String equipoKey
    ) throws EBusinessException {
        return dataProviders.findByKey(equipoKey);
    }

    public List<EntyPrvinvmainventarioequiposDto> findByProveedor(
            String proveedorKey
    ) throws EBusinessException {
        return dataProviders.findByProveedor(proveedorKey);
    }

    public List<EntyPrvinvmainventarioequiposDto> findByTipoEquipo(
            String tipoEquipoKey
    ) throws EBusinessException {
        return dataProviders.findByTipoEquipo(tipoEquipoKey);
    }

    public List<EntyPrvinvmainventarioequiposDto> findByDisponible(
            String disponible
    ) throws EBusinessException {
        return dataProviders.findByDisponible(disponible);
    }

    public List<EntyPrvinvmainventarioequiposDto> findByEstado(
            String estado
    ) throws EBusinessException {
        return dataProviders.findByEstado(estado);
    }
}