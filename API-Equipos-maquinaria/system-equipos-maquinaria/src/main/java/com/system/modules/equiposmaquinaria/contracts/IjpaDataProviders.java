package com.system.modules.equiposmaquinaria.contracts;

import java.util.List;

import com.system.crosscutting.exceptions.Main.EBusinessException;

public interface IjpaDataProviders<T, R> {

    R getAll() throws EBusinessException;

    R getAll(int currentPage, int pageSize, String parameter, String filter) throws EBusinessException;

    T get(Integer id) throws EBusinessException;

    T save(T dto) throws EBusinessException;

    List<T> save(List<T> dto) throws EBusinessException;

    T update(Integer id, T dto) throws EBusinessException;

    void delete(Integer id) throws EBusinessException;
}