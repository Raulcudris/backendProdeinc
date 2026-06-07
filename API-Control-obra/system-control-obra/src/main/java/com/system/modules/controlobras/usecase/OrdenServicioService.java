package com.system.modules.controlobras.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.dataproviders.IjpaOrdenServicioDataProviders;

@Service
public class OrdenServicioService {

    @Autowired
    private IjpaOrdenServicioDataProviders dataProvider;

    public EntyOrsordmaordenservicioResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsordmaordenservicioDto get(Integer id) throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsordmaordenservicioDto saveBefore(
            EntyOrsordmaordenservicioDto dto
    ) throws EBusinessException {

        if (dto.getOrsEstadoregOrde() == null) {
            dto.setOrsEstadoregOrde("1");
        }

        return dataProvider.save(dto);
    }

    public EntyOrsordmaordenservicioDto updateBefore(
            Integer id,
            EntyOrsordmaordenservicioDto dto
    ) throws EBusinessException {
        return dataProvider.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsordmaordenservicioDto dto = dataProvider.get(id);

        if (dto.getOrsPrimarykeyOrde() == null) {
            return "Registro no encontrado";
        }

        dto.setOrsEstadoregOrde(estado);
        dataProvider.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(Integer id) throws EBusinessException {
        dataProvider.delete(id);
        return "Registro eliminado correctamente";
    }
}