package com.system.modules.controlobras.usecase;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.dataproviders.IjpaPlanTrabajoSemanaDataProviders;

@Service
public class EntyPlanSemanalService {

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

        aplicarDefaults(dto);

        return dataProvider.save(dto);
    }

    public List<EntyOrsplamdplantrabsemanaDto> saveBefore(
            final List<EntyOrsplamdplantrabsemanaDto> dtoList
    ) throws EBusinessException {

        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }

        for (EntyOrsplamdplantrabsemanaDto dto : dtoList) {
            aplicarDefaults(dto);
        }

        return dataProvider.save(dtoList);
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

        aplicarDefaults(dto);

        return dataProvider.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsplamdplantrabsemanaDto dto = dataProvider.get(id);

        if (dto == null || dto.getOrsPrimarykeyPlse() == null) {
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

    private void aplicarDefaults(
            EntyOrsplamdplantrabsemanaDto dto
    ) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsEstadoregPlse() == null || dto.getOrsEstadoregPlse().isBlank()) {
            dto.setOrsEstadoregPlse("1");
        }

        if (dto.getOrsTiporegistPlse() == null || dto.getOrsTiporegistPlse().isBlank()) {
            dto.setOrsTiporegistPlse("1");
        }

        if (dto.getOrsEjecutunidadPlse() == null) {
            dto.setOrsEjecutunidadPlse(0);
        }

        if (dto.getOrsValorejecutPlse() == null) {
            dto.setOrsValorejecutPlse(BigDecimal.ZERO);
        }

        if (dto.getOrsCantidunidadPlse() != null &&
                dto.getOrsValorunidadPlse() != null &&
                dto.getOrsValortotalPlse() == null) {
            dto.setOrsValortotalPlse(
                    dto.getOrsValorunidadPlse()
                            .multiply(BigDecimal.valueOf(dto.getOrsCantidunidadPlse()))
            );
        }
    }
}