package com.system.modules.workcontrol.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;

public interface IjpaTipoEvidenciaDataProviders {

    EntyEvitipmatipoevidenciaResponse getAll()
            throws EBusinessException;

    EntyEvitipmatipoevidenciaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException;

    EntyEvitipmatipoevidenciaDto get(Integer id)
            throws EBusinessException;

    EntyEvitipmatipoevidenciaDto save(
            EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException;

    List<EntyEvitipmatipoevidenciaDto> save(
            List<EntyEvitipmatipoevidenciaDto> dtos
    ) throws EBusinessException;

    EntyEvitipmatipoevidenciaDto update(
            Integer id,
            EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException;

    void delete(Integer id)
            throws EBusinessException;

    boolean existsByTipoEvidenciaKey(String tipoEvidenciaKey)
            throws EBusinessException;

    List<EntyEvitipmatipoevidenciaDto> findByEstado(String estado)
            throws EBusinessException;

    EntyEvitipmatipoevidenciaResponse findByEstadoResponse(String estado)
            throws EBusinessException;
}