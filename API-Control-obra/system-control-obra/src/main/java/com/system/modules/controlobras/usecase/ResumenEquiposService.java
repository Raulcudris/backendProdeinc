package com.system.modules.controlobras.usecase;

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

        if (dto.getOrsEstadoregRseq() == null) {
            dto.setOrsEstadoregRseq("1");
        }

        if (dto.getOrsTiporegistRseq() == null) {
            dto.setOrsTiporegistRseq("1");
        }

        return dataProvider.save(dto);
    }

    public EntyOrsordmdresumenequiposDto updateBefore(
            Integer id,
            EntyOrsordmdresumenequiposDto dto
    ) throws EBusinessException {
        return dataProvider.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsordmdresumenequiposDto dto = dataProvider.get(id);

        if (dto.getOrsPrimarykeyRseq() == null) {
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
}