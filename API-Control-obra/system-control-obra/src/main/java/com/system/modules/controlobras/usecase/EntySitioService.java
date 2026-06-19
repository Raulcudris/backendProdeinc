package com.system.modules.controlobras.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosDto;
import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.dataproviders.IjpaSitiosPuntosDataProviders;

@Service
public class EntySitioService {

    @Autowired
    private IjpaSitiosPuntosDataProviders dataProvider;

    public EntyOrsordmdsitiospuntosResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProvider.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyOrsordmdsitiospuntosDto get(Integer id) throws EBusinessException {
        return dataProvider.get(id);
    }

    public EntyOrsordmdsitiospuntosDto saveBefore(
            EntyOrsordmdsitiospuntosDto dto
    ) throws EBusinessException {

        aplicarDefaults(dto);

        return dataProvider.save(dto);
    }

    public List<EntyOrsordmdsitiospuntosDto> saveBefore(
            final List<EntyOrsordmdsitiospuntosDto> dtoList
    ) throws EBusinessException {

        if (dtoList == null || dtoList.isEmpty()) {
            return List.of();
        }

        for (EntyOrsordmdsitiospuntosDto dto : dtoList) {
            aplicarDefaults(dto);
        }

        return dataProvider.save(dtoList);
    }

    public Object findByOrden(String ordenKey) throws EBusinessException {
        return dataProvider.findByOrden(ordenKey);
    }

    public EntyOrsordmdsitiospuntosDto updateBefore(
            Integer id,
            EntyOrsordmdsitiospuntosDto dto
    ) throws EBusinessException {

        aplicarDefaults(dto);

        return dataProvider.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyOrsordmdsitiospuntosDto dto = dataProvider.get(id);

        if (dto == null || dto.getOrsPrimarykeyPunt() == null) {
            return "Registro no encontrado";
        }

        dto.setOrsEstadoregPunt(estado);
        dataProvider.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(Integer id) throws EBusinessException {
        dataProvider.delete(id);
        return "Registro eliminado correctamente";
    }

    private void aplicarDefaults(
            EntyOrsordmdsitiospuntosDto dto
    ) {
        if (dto == null) {
            return;
        }

        if (dto.getOrsEstadoregPunt() == null || dto.getOrsEstadoregPunt().isBlank()) {
            dto.setOrsEstadoregPunt("1");
        }

        if (dto.getOrsTiporegistPunt() == null || dto.getOrsTiporegistPunt().isBlank()) {
            dto.setOrsTiporegistPunt("1");
        }
    }
}