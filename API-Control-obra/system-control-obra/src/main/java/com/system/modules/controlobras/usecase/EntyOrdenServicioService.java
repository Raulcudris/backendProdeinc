package com.system.modules.controlobras.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.dataproviders.IjpaOrdenServicioDataProviders;

@Service
public class EntyOrdenServicioService {

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

        aplicarDefaults(dto);

        return dataProvider.save(dto);
    }

    public List<EntyOrsordmaordenservicioDto> saveBefore(
            final List<EntyOrsordmaordenservicioDto> dtoList
    ) throws EBusinessException {

        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }

        for (EntyOrsordmaordenservicioDto dto : dtoList) {
            aplicarDefaults(dto);
        }

        return dataProvider.save(dtoList);
    }

    public EntyOrsordmaordenservicioDto updateBefore(
            Integer id,
            EntyOrsordmaordenservicioDto dto
    ) throws EBusinessException {

        aplicarDefaults(dto);

        return dataProvider.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsordmaordenservicioDto dto = dataProvider.get(id);

        if (dto == null || dto.getOrsPrimarykeyOrde() == null) {
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

    private void aplicarDefaults(
            EntyOrsordmaordenservicioDto dto
    ) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsEstadoregOrde() == null || dto.getOrsEstadoregOrde().isBlank()) {
            dto.setOrsEstadoregOrde("1");
        }
    }
}