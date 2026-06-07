package com.system.modules.controlobras.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoDto;
import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.dataproviders.IjpaPlanTrabajoDataProviders;

@Service
public class PlanTrabajoService {

    @Autowired
    private IjpaPlanTrabajoDataProviders dataProvider;

    public EntyOrsplamaplandetrabajoResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsplamaplandetrabajoDto get(Integer id) throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsplamaplandetrabajoDto saveBefore(
            EntyOrsplamaplandetrabajoDto dto
    ) throws EBusinessException {

        if (dto.getOrsEstadoregPltr() == null) {
            dto.setOrsEstadoregPltr("1");
        }

        if (dto.getOrsTiporegistPltr() == null) {
            dto.setOrsTiporegistPltr("1");
        }

        return dataProvider.save(dto);
    }

    public EntyOrsplamaplandetrabajoDto updateBefore(
            Integer id,
            EntyOrsplamaplandetrabajoDto dto
    ) throws EBusinessException {
        return dataProvider.update(id, dto);
    }

    public Object findByOrden(String ordenKey) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public Object findByPunto(String puntoKey) throws EBusinessException {
        return dataProvider.findByPunto(puntoKey);
    }
    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsplamaplandetrabajoDto dto = dataProvider.get(id);

        if (dto.getOrsPrimarykeyPltr() == null) {
            return "Registro no encontrado";
        }

        dto.setOrsEstadoregPltr(estado);
        dataProvider.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(Integer id) throws EBusinessException {
        dataProvider.delete(id);
        return "Registro eliminado correctamente";
    }
}