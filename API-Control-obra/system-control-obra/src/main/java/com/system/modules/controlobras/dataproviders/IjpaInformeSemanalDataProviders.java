package com.system.modules.controlobras.contracts;

import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamainformesemanalDto;
import com.system.crosscutting.domain.model.EntyOrsplamainformesemanalResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;

public interface IjpaInformeSemanalDataProviders extends IjpaDataProviders<
        EntyOrsplamainformesemanalDto,
        EntyOrsplamainformesemanalResponse> {

    EntyOrsplamainformesemanalDto findByKey(String informeSemanalKey) throws EBusinessException;

    List<EntyOrsplamainformesemanalDto> findByOrden(String ordenKey) throws EBusinessException;

    List<EntyOrsplamainformesemanalDto> findByProyeccionSemana(String proyeccionSemanaKey) throws EBusinessException;

    List<EntyOrsplamainformesemanalDto> findBySemana(Integer semana) throws EBusinessException;

    List<EntyOrsplamainformesemanalDto> findByEstado(String estado) throws EBusinessException;

    EntyOrsplamainformesemanalDto changestatus(Integer id, String status) throws EBusinessException;
}