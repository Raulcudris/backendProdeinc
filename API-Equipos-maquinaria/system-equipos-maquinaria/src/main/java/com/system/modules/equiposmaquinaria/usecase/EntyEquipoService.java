package com.system.modules.equiposmaquinaria.usecase;

import java.util.ArrayList;
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

        if (dto.getPrvIdentifkeyInve() == null ||
                dto.getPrvIdentifkeyInve().isBlank()) {
            return new EntyPrvinvmainventarioequiposDto();
        }

        normalizeDto(dto);

        return dataProviders.save(dto);
    }

    public List<EntyPrvinvmainventarioequiposDto> saveBefore(
            List<EntyPrvinvmainventarioequiposDto> dtos
    ) throws EBusinessException {
        List<EntyPrvinvmainventarioequiposDto> result = new ArrayList<>();

        for (EntyPrvinvmainventarioequiposDto dto : dtos) {
            result.add(saveBefore(dto));
        }

        return result;
    }

    public EntyPrvinvmainventarioequiposDto updateBefore(
            Integer id,
            EntyPrvinvmainventarioequiposDto dto
    ) throws EBusinessException {

        if (id == null) {
            return new EntyPrvinvmainventarioequiposDto();
        }

        normalizeDtoForUpdate(dto);

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

    public String deleteBefore(
            Integer id
    ) throws EBusinessException {
        dataProviders.delete(id);
        return "Registro eliminado correctamente";
    }

    public EntyPrvinvmainventarioequiposDto findByKey(
            String equipoKey
    ) throws EBusinessException {

        if (equipoKey == null || equipoKey.isBlank()) {
            return new EntyPrvinvmainventarioequiposDto();
        }

        return dataProviders.findByKey(equipoKey.trim().toUpperCase());
    }

    public List<EntyPrvinvmainventarioequiposDto> findByProveedor(
            String proveedorKey
    ) throws EBusinessException {

        if (proveedorKey == null || proveedorKey.isBlank()) {
            return new ArrayList<>();
        }

        return dataProviders.findByProveedor(proveedorKey.trim().toUpperCase());
    }

    public List<EntyPrvinvmainventarioequiposDto> findByTipoEquipo(
            String tipoEquipoKey
    ) throws EBusinessException {

        if (tipoEquipoKey == null || tipoEquipoKey.isBlank()) {
            return new ArrayList<>();
        }

        return dataProviders.findByTipoEquipo(tipoEquipoKey.trim().toUpperCase());
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

    private void normalizeDto(
            EntyPrvinvmainventarioequiposDto dto
    ) {
        dto.setPrvIdentifkeyInve(dto.getPrvIdentifkeyInve().trim().toUpperCase());

        if (dto.getPrvIdentifkeyMprv() != null &&
                !dto.getPrvIdentifkeyMprv().isBlank()) {
            dto.setPrvIdentifkeyMprv(dto.getPrvIdentifkeyMprv().trim().toUpperCase());
        }

        if (dto.getPrvTipoequipoTieq() != null &&
                !dto.getPrvTipoequipoTieq().isBlank()) {
            dto.setPrvTipoequipoTieq(dto.getPrvTipoequipoTieq().trim().toUpperCase());
        }

        if (dto.getPrvNombrequipoInve() == null ||
                dto.getPrvNombrequipoInve().isBlank()) {
            dto.setPrvNombrequipoInve("Sin nombre");
        }

        if (dto.getPrvRefermodeloInve() == null ||
                dto.getPrvRefermodeloInve().isBlank()) {
            dto.setPrvRefermodeloInve("Sin referencia");
        }

        if (dto.getPrvEquipoestadoInve() == null ||
                dto.getPrvEquipoestadoInve().isBlank()) {
            dto.setPrvEquipoestadoInve("OPE");
        }

        if (dto.getPrvEquipoactivoInve() == null ||
                dto.getPrvEquipoactivoInve().isBlank()) {
            dto.setPrvEquipoactivoInve("1");
        }

        if (dto.getPrvEstadoregInve() == null ||
                dto.getPrvEstadoregInve().isBlank()) {
            dto.setPrvEstadoregInve("1");
        }

        if (dto.getPrvDescripcionInve() == null ||
                dto.getPrvDescripcionInve().isBlank()) {
            dto.setPrvDescripcionInve("Sin descripcion");
        }
    }

    private void normalizeDtoForUpdate(
            EntyPrvinvmainventarioequiposDto dto
    ) {
        if (dto.getPrvIdentifkeyInve() != null &&
                !dto.getPrvIdentifkeyInve().isBlank()) {
            dto.setPrvIdentifkeyInve(dto.getPrvIdentifkeyInve().trim().toUpperCase());
        }

        if (dto.getPrvIdentifkeyMprv() != null &&
                !dto.getPrvIdentifkeyMprv().isBlank()) {
            dto.setPrvIdentifkeyMprv(dto.getPrvIdentifkeyMprv().trim().toUpperCase());
        }

        if (dto.getPrvTipoequipoTieq() != null &&
                !dto.getPrvTipoequipoTieq().isBlank()) {
            dto.setPrvTipoequipoTieq(dto.getPrvTipoequipoTieq().trim().toUpperCase());
        }

        if (dto.getPrvEquipoestadoInve() == null ||
                dto.getPrvEquipoestadoInve().isBlank()) {
            dto.setPrvEquipoestadoInve("OPE");
        }

        if (dto.getPrvEquipoactivoInve() == null ||
                dto.getPrvEquipoactivoInve().isBlank()) {
            dto.setPrvEquipoactivoInve("1");
        }

        if (dto.getPrvEstadoregInve() == null ||
                dto.getPrvEstadoregInve().isBlank()) {
            dto.setPrvEstadoregInve("1");
        }
    }
}