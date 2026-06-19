package com.system.modules.controlobras.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsordmdresumenequiposDto;
import com.system.crosscutting.domain.model.EntyOrsordmdresumenequiposResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.dataproviders.IjpaResumenEquiposDataProviders;

@Service
public class ResumenEquiposService {

    @Autowired
    private IjpaResumenEquiposDataProviders dataProvider;

    public EntyOrsordmdresumenequiposResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public Object findByOrden(String ordenKey) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public EntyOrsordmdresumenequiposDto get(Integer id) throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsordmdresumenequiposDto saveBefore(
            EntyOrsordmdresumenequiposDto dto
    ) throws EBusinessException {

        aplicarDefaults(dto);

        return dataProvider.save(dto);
    }

    public List<EntyOrsordmdresumenequiposDto> saveBefore(
            final List<EntyOrsordmdresumenequiposDto> dtoList
    ) throws EBusinessException {

        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }

        for (EntyOrsordmdresumenequiposDto dto : dtoList) {
            aplicarDefaults(dto);
        }

        return dataProvider.save(dtoList);
    }

    public EntyOrsordmdresumenequiposDto updateBefore(
            Integer id,
            EntyOrsordmdresumenequiposDto dto
    ) throws EBusinessException {

        aplicarDefaults(dto);

        return dataProvider.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsordmdresumenequiposDto dto = dataProvider.get(id);

        if (dto == null || dto.getOrsPrimarykeyRseq() == null) {
            return "Registro no encontrado";
        }

        dto.setOrsEstadoregRseq(estado);
        dataProvider.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(Integer id) throws EBusinessException {
        dataProvider.delete(id);
        return "Registro eliminado correctamente";
    }

    private void aplicarDefaults(
            EntyOrsordmdresumenequiposDto dto
    ) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsEstadoregRseq() == null || dto.getOrsEstadoregRseq().isBlank()) {
            dto.setOrsEstadoregRseq("1");
        }

        if (dto.getOrsTiporegistRseq() == null || dto.getOrsTiporegistRseq().isBlank()) {
            dto.setOrsTiporegistRseq("1");
        }
    }
}