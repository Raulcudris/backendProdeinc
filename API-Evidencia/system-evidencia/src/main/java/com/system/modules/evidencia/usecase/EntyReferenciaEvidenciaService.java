package com.system.modules.evidencia.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.dataproviders.IjpaReferenciaEvidenciaDataProviders;

@Service
public class EntyReferenciaEvidenciaService {

    @Autowired
    private IjpaReferenciaEvidenciaDataProviders dataProviders;

    public EntyEvirefmdreferenciaResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyEvirefmdreferenciaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyEvirefmdreferenciaDto get(
            Integer id
    ) throws EBusinessException {
        return dataProviders.get(id);
    }

    public EntyEvirefmdreferenciaDto saveBefore(
            EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {

        if (dto.getEviTiporegistRefe() == null || dto.getEviTiporegistRefe().isBlank()) {
            dto.setEviTiporegistRefe("1");
        }

        if (dto.getEviEstadoregRefe() == null || dto.getEviEstadoregRefe().isBlank()) {
            dto.setEviEstadoregRefe("1");
        }

        return dataProviders.save(dto);
    }

    public List<EntyEvirefmdreferenciaDto> saveBefore(
            List<EntyEvirefmdreferenciaDto> dtos
    ) throws EBusinessException {
        return dataProviders.save(dtos);
    }

    public EntyEvirefmdreferenciaDto updateBefore(
            Integer id,
            EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {
        return dataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyEvirefmdreferenciaDto dto = dataProviders.get(id);

        if (dto == null || dto.getEviPrimarykeyRefe() == null) {
            return "No existe la referencia de evidencia con id: " + id;
        }

        dto.setEviEstadoregRefe(estado);

        dataProviders.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(
            Integer id
    ) throws EBusinessException {
        dataProviders.delete(id);
        return "Registro eliminado correctamente";
    }

    public List<EntyEvirefmdreferenciaDto> findByEvidencia(
            String evidenciaKey
    ) throws EBusinessException {
        return dataProviders.findByEvidencia(evidenciaKey);
    }

    public List<EntyEvirefmdreferenciaDto> findByRegistro(
            String registroKey
    ) throws EBusinessException {
        return dataProviders.findByRegistro(registroKey);
    }

    public List<EntyEvirefmdreferenciaDto> findByTipoRegistro(
            String tipoRegistro
    ) throws EBusinessException {
        return dataProviders.findByTipoRegistro(tipoRegistro);
    }

    public List<EntyEvirefmdreferenciaDto> findByTipoRegistroAndRegistro(
            String tipoRegistro,
            String registroKey
    ) throws EBusinessException {
        return dataProviders.findByTipoRegistroAndRegistro(tipoRegistro, registroKey);
    }

    public List<EntyEvirefmdreferenciaDto> findByEstado(
            String estado
    ) throws EBusinessException {
        return dataProviders.findByEstado(estado);
    }
}