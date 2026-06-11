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

    /*
     * Se mantiene por compatibilidad, pero para unidad de medida
     * se debe consultar por unidadKey.
     */
    public EntyPrvinvmdunidamedequipoDto get(
            Integer id
    ) throws EBusinessException {
        return dataProviders.get(id);
    }

    public EntyPrvinvmdunidamedequipoDto saveBefore(
            EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {

        if (dto.getPrvTipunidamedUnme() == null || dto.getPrvTipunidamedUnme().isBlank()) {
            return new EntyPrvinvmdunidamedequipoDto();
        }

        dto.setPrvTipunidamedUnme(dto.getPrvTipunidamedUnme().trim().toUpperCase());

        if (dto.getPrvDescmedidaUnme() == null || dto.getPrvDescmedidaUnme().isBlank()) {
            dto.setPrvDescmedidaUnme("Sin descripcion");
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

    /*
     * Se mantiene por compatibilidad, pero para unidad de medida
     * se debe actualizar por unidadKey.
     */
    public EntyPrvinvmdunidamedequipoDto updateBefore(
            Integer id,
            EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {
        return dataProviders.update(id, dto);
    }

    public EntyPrvinvmdunidamedequipoDto updateByKey(
            String unidadKey,
            EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {

        if (unidadKey == null || unidadKey.isBlank()) {
            return new EntyPrvinvmdunidamedequipoDto();
        }

        String key = unidadKey.trim().toUpperCase();
        dto.setPrvTipunidamedUnme(key);

        if (dto.getPrvDescmedidaUnme() == null || dto.getPrvDescmedidaUnme().isBlank()) {
            dto.setPrvDescmedidaUnme("Sin descripcion");
        }

        if (dto.getPrvEstadoregUnme() == null || dto.getPrvEstadoregUnme().isBlank()) {
            dto.setPrvEstadoregUnme("1");
        }

        return dataProviders.updateByKey(key, dto);
    }

    /*
     * Se mantiene por compatibilidad, pero para unidad de medida
     * se debe cambiar estado por unidadKey.
     */
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

    public String changestatusByKey(
            String unidadKey,
            String estado
    ) throws EBusinessException {

        if (unidadKey == null || unidadKey.isBlank()) {
            return "La key de la unidad de medida es obligatoria";
        }

        String key = unidadKey.trim().toUpperCase();

        EntyPrvinvmdunidamedequipoDto dto = dataProviders.findByKey(key);

        if (dto == null || dto.getPrvTipunidamedUnme() == null) {
            return "No existe la unidad de medida con key: " + key;
        }

        dto.setPrvEstadoregUnme(estado);

        dataProviders.updateByKey(key, dto);

        return "Estado actualizado correctamente";
    }

    /*
     * Se mantiene por compatibilidad, pero para unidad de medida
     * se debe eliminar por unidadKey.
     */
    public String deleteBefore(
            Integer id
    ) throws EBusinessException {
        dataProviders.delete(id);
        return "Registro eliminado correctamente";
    }

    public String deleteByKey(
            String unidadKey
    ) throws EBusinessException {

        if (unidadKey == null || unidadKey.isBlank()) {
            return "La key de la unidad de medida es obligatoria";
        }

        dataProviders.deleteByKey(unidadKey.trim().toUpperCase());

        return "Registro eliminado correctamente";
    }

    public EntyPrvinvmdunidamedequipoDto findByKey(
            String unidadKey
    ) throws EBusinessException {

        if (unidadKey == null || unidadKey.isBlank()) {
            return new EntyPrvinvmdunidamedequipoDto();
        }

        return dataProviders.findByKey(unidadKey.trim().toUpperCase());
    }

    public List<EntyPrvinvmdunidamedequipoDto> findByEstado(
            String estado
    ) throws EBusinessException {
        return dataProviders.findByEstado(estado);
    }
}