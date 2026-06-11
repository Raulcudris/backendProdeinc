package com.system.modules.controlobras.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsordmdproyecsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsordmdproyecsemanaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.dataproviders.IjpaProyeccionSemanaDataProviders;

@Service
public class EntyProyeccionSemanaService {

    @Autowired
    private IjpaProyeccionSemanaDataProviders dataProvider;

    public EntyOrsordmdproyecsemanaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsordmdproyecsemanaDto get(Integer id) throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsordmdproyecsemanaDto saveBefore(
            EntyOrsordmdproyecsemanaDto dto
    ) throws EBusinessException {

        if (dto.getOrsEstadoregPsem() == null) {
            dto.setOrsEstadoregPsem("1");
        }

        if (dto.getOrsTiporegistPsem() == null) {
            dto.setOrsTiporegistPsem("1");
        }

        return dataProvider.save(dto);
    }

    public EntyOrsordmdproyecsemanaDto updateBefore(
            Integer id,
            EntyOrsordmdproyecsemanaDto dto
    ) throws EBusinessException {
        return dataProvider.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsordmdproyecsemanaDto dto = dataProvider.get(id);

        if (dto.getOrsPrimarykeyPsem() == null) {
            return "Registro no encontrado";
        }

        dto.setOrsEstadoregPsem(estado);
        dataProvider.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public Object findByOrden(String ordenKey) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public String deleteBefore(Integer id) throws EBusinessException {
        dataProvider.delete(id);
        return "Registro eliminado correctamente";
    }
}