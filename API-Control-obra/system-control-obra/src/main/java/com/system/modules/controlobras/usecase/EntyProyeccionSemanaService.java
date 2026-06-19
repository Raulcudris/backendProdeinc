package com.system.modules.controlobras.usecase;

import java.util.List;

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

        aplicarDefaults(dto);

        return dataProvider.save(dto);
    }

    public List<EntyOrsordmdproyecsemanaDto> saveBefore(
            final List<EntyOrsordmdproyecsemanaDto> dtoList
    ) throws EBusinessException {

        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }

        for (EntyOrsordmdproyecsemanaDto dto : dtoList) {
            aplicarDefaults(dto);
        }

        return dataProvider.save(dtoList);
    }

    public EntyOrsordmdproyecsemanaDto updateBefore(
            Integer id,
            EntyOrsordmdproyecsemanaDto dto
    ) throws EBusinessException {

        aplicarDefaults(dto);

        return dataProvider.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsordmdproyecsemanaDto dto = dataProvider.get(id);

        if (dto == null || dto.getOrsPrimarykeyPsem() == null) {
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

    private void aplicarDefaults(
            EntyOrsordmdproyecsemanaDto dto
    ) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsEstadoregPsem() == null || dto.getOrsEstadoregPsem().isBlank()) {
            dto.setOrsEstadoregPsem("1");
        }

        if (dto.getOrsTiporegistPsem() == null || dto.getOrsTiporegistPsem().isBlank()) {
            dto.setOrsTiporegistPsem("1");
        }
    }
}