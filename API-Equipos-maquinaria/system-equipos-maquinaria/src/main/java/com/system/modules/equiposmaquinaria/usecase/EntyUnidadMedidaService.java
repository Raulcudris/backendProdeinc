package com.system.modules.equiposmaquinaria.usecase;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyPrvinvmdunidamedequipoDto;
import com.system.crosscutting.domain.model.EntyPrvinvmdunidamedequipoResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.dataproviders.IjpaUnidadMedidaDataProviders;

@Service
public class EntyUnidadMedidaService {

    @Autowired
    private IjpaUnidadMedidaDataProviders dataProviders;

    public EntyPrvinvmdunidamedequipoResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyPrvinvmdunidamedequipoResponse getAll(
            int currentPage,
            int pageSize,
            String parameter,
            String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyPrvinvmdunidamedequipoDto get(
            Integer id
    ) throws EBusinessException {
        return dataProviders.get(id);
    }

    public EntyPrvinvmdunidamedequipoDto saveBefore(
            EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {

        if (dto.getPrvTipunidamedUnme() == null || dto.getPrvTipunidamedUnme().isBlank()) {
            dto.setPrvTipunidamedUnme("HORA");
        }

        if (dto.getPrvDescmedidaUnme() == null || dto.getPrvDescmedidaUnme().isBlank()) {
            dto.setPrvDescmedidaUnme("Hora de trabajo");
        }

        if (dto.getPrvEstadoregUnme() == null || dto.getPrvEstadoregUnme().isBlank()) {
            dto.setPrvEstadoregUnme("1");
        }

        return dataProviders.save(dto);
    }

    public List<EntyPrvinvmdunidamedequipoDto> saveBefore(
            List<EntyPrvinvmdunidamedequipoDto> dtos
    ) throws EBusinessException {
        return dataProviders.save(dtos);
    }

    public EntyPrvinvmdunidamedequipoDto updateBefore(
            Integer id,
            EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {

        if (dto.getPrvTipunidamedUnme() == null || dto.getPrvTipunidamedUnme().isBlank()) {
            dto.setPrvTipunidamedUnme("HORA");
        }

        if (dto.getPrvDescmedidaUnme() == null || dto.getPrvDescmedidaUnme().isBlank()) {
            dto.setPrvDescmedidaUnme("Hora de trabajo");
        }

        if (dto.getPrvEstadoregUnme() == null || dto.getPrvEstadoregUnme().isBlank()) {
            dto.setPrvEstadoregUnme("1");
        }

        return dataProviders.update(id, dto);
    }

    public String changestatus(
            Integer id,
            String estado
    ) throws EBusinessException {

        EntyPrvinvmdunidamedequipoDto dto = dataProviders.get(id);

        if (dto == null || dto.getPrvTipunidamedUnme() == null) {
            return "No existe la unidad de medida con id: " + id;
        }

        dto.setPrvEstadoregUnme(estado);

        dataProviders.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(
            Integer id
    ) throws EBusinessException {
        dataProviders.delete(id);
        return "Registro eliminado correctamente";
    }

    public EntyPrvinvmdunidamedequipoDto findByKey(
            String unidadKey
    ) throws EBusinessException {
        return dataProviders.findByKey(unidadKey);
    }

    public List<EntyPrvinvmdunidamedequipoDto> findByEstado(
            String estado
    ) throws EBusinessException {
        return dataProviders.findByEstado(estado);
    }
}