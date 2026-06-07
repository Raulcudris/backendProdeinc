package com.system.modules.evidencia.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.dataproviders.IjpaTipoEvidenciaDataProviders;

@Service
public class EntyTipoEvidenciaService {

    @Autowired
    private IjpaTipoEvidenciaDataProviders dataProviders;

    public EntyEvitipmatipoevidenciaResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyEvitipmatipoevidenciaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEvitipmatipoevidenciaDto get(
            Integer id
    ) throws EBusinessException {
        return dataProviders.get(id);
    }

    public EntyEvitipmatipoevidenciaDto saveBefore(
            EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {

        if (dto.getEviTiporegistTiev() == null || dto.getEviTiporegistTiev().isBlank()) {
            dto.setEviTiporegistTiev("1");
        }

        if (dto.getEviEstadoregTiev() == null || dto.getEviEstadoregTiev().isBlank()) {
            dto.setEviEstadoregTiev("1");
        }

        return dataProviders.save(dto);
    }

    public List<EntyEvitipmatipoevidenciaDto> saveBefore(
            List<EntyEvitipmatipoevidenciaDto> dtos
    ) throws EBusinessException {
        return dataProviders.save(dtos);
    }

    public EntyEvitipmatipoevidenciaDto updateBefore(
            Integer id,
            EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException {
        return dataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyEvitipmatipoevidenciaDto dto = dataProviders.get(id);

        if (dto == null || dto.getEviPrimarykeyTiev() == null) {
            return "No existe el tipo de evidencia con id: " + id;
        }

        dto.setEviEstadoregTiev(estado);

        dataProviders.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(
            Integer id
    ) throws EBusinessException {
        dataProviders.delete(id);
        return "Registro eliminado correctamente";
    }

    public List<EntyEvitipmatipoevidenciaDto> findByEstado(
            String estado
    ) throws EBusinessException {
        return dataProviders.findByEstado(estado);
    }
}