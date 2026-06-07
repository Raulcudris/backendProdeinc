package com.system.modules.controlobras.usecase;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriDto;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.dataproviders.IjpaNovedadHistoriDataProviders;

@Service
public class NovedadService {

    @Autowired
    private IjpaNovedadHistoriDataProviders dataProvider;

    public EntyOrsconfnovedadhistoriResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsconfnovedadhistoriDto get(Integer id) throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsconfnovedadhistoriDto saveBefore(
            EntyOrsconfnovedadhistoriDto dto
    ) throws EBusinessException {

        if (dto.getOrsEstadoregNove() == null) {
            dto.setOrsEstadoregNove("1");
        }

        if (dto.getOrsFechreportNove() == null) {
            dto.setOrsFechreportNove(LocalDate.now());
        }

        return dataProvider.save(dto);
    }

    public EntyOrsconfnovedadhistoriDto updateBefore(
            Integer id,
            EntyOrsconfnovedadhistoriDto dto
    ) throws EBusinessException {
        return dataProvider.update(id, dto);
    }

    public Object findByOrden(String ordenKey) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public Object findByRegistroBase(String registroBase) throws EBusinessException {
        return dataProvider.findByRegistroBase(registroBase);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsconfnovedadhistoriDto dto = dataProvider.get(id);

        if (dto.getOrsPrimarykeyNove() == null) {
            return "Registro no encontrado";
        }

        dto.setOrsEstadoregNove(estado);
        dataProvider.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(Integer id) throws EBusinessException {
        dataProvider.delete(id);
        return "Registro eliminado correctamente";
    }
}