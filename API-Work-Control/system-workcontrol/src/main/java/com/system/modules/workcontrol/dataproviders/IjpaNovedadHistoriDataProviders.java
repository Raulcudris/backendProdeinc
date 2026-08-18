package com.system.modules.workcontrol.dataproviders;

import java.time.LocalDate;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriDto;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.contracts.IjpaDataProviders;

public interface IjpaNovedadHistoriDataProviders
        extends IjpaDataProviders<
        EntyOrsconfnovedadhistoriDto,
        EntyOrsconfnovedadhistoriResponse
        > {

    boolean existsByNovedadKey(
            String novedadKey
    ) throws EBusinessException;

    List<EntyOrsconfnovedadhistoriDto> findByOrden(
            String ordenKey
    ) throws EBusinessException;

    List<EntyOrsconfnovedadhistoriDto> findByTipo(
            String tipoNovedad
    ) throws EBusinessException;

    List<EntyOrsconfnovedadhistoriDto> findByRegistroBase(
            String registroBase
    ) throws EBusinessException;

    List<EntyOrsconfnovedadhistoriDto> findByRegistroNovedad(
            String registroNovedad
    ) throws EBusinessException;

    List<EntyOrsconfnovedadhistoriDto> findByFecha(
            LocalDate fechaReporte
    ) throws EBusinessException;

    EntyOrsconfnovedadhistoriResponse findByOrdenResponse(
            String ordenKey
    ) throws EBusinessException;

    EntyOrsconfnovedadhistoriResponse findByTipoResponse(
            String tipoNovedad
    ) throws EBusinessException;
}