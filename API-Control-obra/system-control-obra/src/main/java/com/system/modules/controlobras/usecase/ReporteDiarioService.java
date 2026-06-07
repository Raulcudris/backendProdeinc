package com.system.modules.controlobras.usecase;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.dataproviders.IjpaReporteDiarioDataProviders;

@Service
public class ReporteDiarioService {

    @Autowired
    private IjpaReporteDiarioDataProviders dataProvider;

    public EntyOrsplamdreportediarioResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsplamdreportediarioDto get(Integer id) throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsplamdreportediarioDto saveBefore(
            EntyOrsplamdreportediarioDto dto
    ) throws EBusinessException {

        if (dto.getOrsEstadoregPdia() == null) {
            dto.setOrsEstadoregPdia("1");
        }

        if (dto.getOrsTiporegistPdia() == null) {
            dto.setOrsTiporegistPdia("1");
        }

        if (dto.getOrsFechasistemaPdia() == null) {
            dto.setOrsFechasistemaPdia(LocalDate.now());
        }

        return dataProvider.save(dto);
    }

    public Object findByOrden(String ordenKey) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public Object findByPlanSemana(String planSemanalKey) throws EBusinessException {
        return dataProvider.findByPlanSemana(planSemanalKey);
    }

    public Object findByProyeccionSemana(String proyeccionKey) throws EBusinessException {
        return dataProvider.findByProyeccionSemana(proyeccionKey);
    }

    public EntyOrsplamdreportediarioDto updateBefore(
            Integer id,
            EntyOrsplamdreportediarioDto dto
    ) throws EBusinessException {
        return dataProvider.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsplamdreportediarioDto dto = dataProvider.get(id);

        if (dto.getOrsPrimarykeyPdia() == null) {
            return "Registro no encontrado";
        }

        dto.setOrsEstadoregPdia(estado);
        dataProvider.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(Integer id) throws EBusinessException {
        dataProvider.delete(id);
        return "Registro eliminado correctamente";
    }
}