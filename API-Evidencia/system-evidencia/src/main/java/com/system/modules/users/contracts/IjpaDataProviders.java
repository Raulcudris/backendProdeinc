package com.system.modules.users.contracts;

import java.util.List;

import com.system.crosscutting.domain.model.EntyRecmaesusuarimaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;

public interface  IjpaDataProviders<T> {

    EntyRecmaesusuarimaResponse getAll() throws EBusinessException;

    EntyRecmaesusuarimaResponse getAll(int currentPage , int pageSize,String parameter, String filter) throws EBusinessException;

    T get(Integer id) throws EBusinessException;

    T save(T dto) throws EBusinessException;

    List<T> save(List<T> dto) throws EBusinessException;

    T update(Integer id, T dto) throws EBusinessException;

    T updateImage(Integer id, T dto) throws EBusinessException;

    void delete(Integer id) throws EBusinessException;
}
