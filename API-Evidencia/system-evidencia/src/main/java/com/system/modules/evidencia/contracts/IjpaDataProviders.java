package com.system.modules.evidencia.contracts;

import java.util.List;

import com.system.crosscutting.exceptions.Main.EBusinessException;

public interface IjpaDataProviders<D, R> {

    R getAll() throws EBusinessException;

    R getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException;

    D get(
            Integer id
    ) throws EBusinessException;

    D save(
            D dto
    ) throws EBusinessException;

    List<D> save(
            List<D> dtos
    ) throws EBusinessException;

    D update(
            Integer id,
            D dto
    ) throws EBusinessException;

    void delete(
            Integer id
    ) throws EBusinessException;
}