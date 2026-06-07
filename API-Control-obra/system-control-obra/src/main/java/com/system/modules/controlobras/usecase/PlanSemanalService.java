package com.system.modules.controlobras.usecase;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.dataproviders.IjpaPlanTrabajoSemanaDataProviders;

@Service
public class PlanSemanalService {

    @Autowired
    private IjpaPlanTrabajoSemanaDataProviders dataProvider;

    public EntyOrsplamdplantrabsemanaResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsplamdplantrabsemanaDto get(Integer id) throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsplamdplantrabsemanaDto saveBefore(
            EntyOrsplamdplantrabsemanaDto dto
    ) throws EBusinessException {

        if (dto.getOrsEstadoregPlse() == null) {
            dto.setOrsEstadoregPlse("1");
        }

        if (dto.getOrsTiporegistPlse() == null) {
            dto.setOrsTiporegistPlse("1");
        }

        if (dto.getOrsEjecutunidadPlse() == null) {
            dto.setOrsEjecutunidadPlse(0);
        }

        if (dto.getOrsValorejecutPlse() == null) {
            dto.setOrsValorejecutPlse(BigDecimal.ZERO);
        }

        return dataProvider.save(dto);
    }

    public Object findByOrden(String ordenKey) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public Object findByPlan(String planKey) throws EBusinessException {
        return dataProvider.findByPlan(planKey);
    }

    public Object findByProyeccionSemana(String proyeccionKey) throws EBusinessException {
        return dataProvider.findByProyeccionSemana(proyeccionKey);
    }

    public EntyOrsplamdplantrabsemanaDto updateBefore(
            Integer id,
            EntyOrsplamdplantrabsemanaDto dto
    ) throws EBusinessException {
        return dataProvider.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsplamdplantrabsemanaDto dto = dataProvider.get(id);

        if (dto.getOrsPrimarykeyPlse() == null) {
            return "Registro no encontrado";
        }

        dto.setOrsEstadoregPlse(estado);
        dataProvider.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(Integer id) throws EBusinessException {
        dataProvider.delete(id);
        return "Registro eliminado correctamente";
    }
}