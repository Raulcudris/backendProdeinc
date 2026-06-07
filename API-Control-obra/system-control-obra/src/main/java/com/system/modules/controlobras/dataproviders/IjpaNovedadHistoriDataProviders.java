package com.system.modules.controlobras.dataproviders;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriDto;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.contracts.IjpaDataProviders;

public interface IjpaNovedadHistoriDataProviders
        extends IjpaDataProviders<EntyOrsconfnovedadhistoriDto, EntyOrsconfnovedadhistoriResponse> {

    List<EntyOrsconfnovedadhistoriDto> findByOrden(String ordenKey) throws EBusinessException;

    List<EntyOrsconfnovedadhistoriDto> findByRegistroBase(String registroBase) throws EBusinessException;
}