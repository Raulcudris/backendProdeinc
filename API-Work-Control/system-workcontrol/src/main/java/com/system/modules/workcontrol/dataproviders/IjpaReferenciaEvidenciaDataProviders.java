package com.system.modules.workcontrol.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;

public interface IjpaReferenciaEvidenciaDataProviders {

    EntyEvirefmdreferenciaResponse getAll()
            throws EBusinessException;

    EntyEvirefmdreferenciaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException;

    EntyEvirefmdreferenciaDto get(Integer id)
            throws EBusinessException;

    EntyEvirefmdreferenciaDto save(
            EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException;

    List<EntyEvirefmdreferenciaDto> save(
            List<EntyEvirefmdreferenciaDto> dtos
    ) throws EBusinessException;

    EntyEvirefmdreferenciaDto update(
            Integer id,
            EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException;

    void delete(Integer id)
            throws EBusinessException;

    boolean existsByReferenciaKey(String referenciaKey)
            throws EBusinessException;

    List<EntyEvirefmdreferenciaDto> findByEvidencia(String evidenciaKey)
            throws EBusinessException;

    List<EntyEvirefmdreferenciaDto> findByTipoRegistro(String tipoRegistro)
            throws EBusinessException;

    List<EntyEvirefmdreferenciaDto> findByRegistro(
            String tipoRegistro,
            String identificadorRegistro
    ) throws EBusinessException;

    EntyEvirefmdreferenciaResponse findByEvidenciaResponse(String evidenciaKey)
            throws EBusinessException;

    EntyEvirefmdreferenciaResponse findByRegistroResponse(
            String tipoRegistro,
            String identificadorRegistro
    ) throws EBusinessException;
}