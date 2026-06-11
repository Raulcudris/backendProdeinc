package com.system.modules.equiposmaquinaria.usecase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyPrvinvmdunidamedequipoDto;
import com.system.crosscutting.domain.model.EntyPrvinvmdunidamedequipoResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.dataproviders.IjpaUnidadMedidaDataProviders;

@Service
public class EntyUnidadMedidaService {

    private static final String ESTADO_REGISTRO_ACTIVO = "1";
    private static final String ESTADO_REGISTRO_INACTIVO = "2";

    @Autowired
    private IjpaUnidadMedidaDataProviders dataProviders;

    public EntyPrvinvmdunidamedequipoResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyPrvinvmdunidamedequipoResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyPrvinvmdunidamedequipoDto get(
            final Integer id
    ) throws EBusinessException {
        validarId(id);
        return dataProviders.get(id);
    }

    public EntyPrvinvmdunidamedequipoDto saveBefore(
            final EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {

        aplicarReglasCreacion(dto);
        return dataProviders.save(dto);
    }

    public List<EntyPrvinvmdunidamedequipoDto> saveBefore(
            final List<EntyPrvinvmdunidamedequipoDto> dtos
    ) throws EBusinessException {

        if (dtos == null || dtos.isEmpty()) {
            throw new EBusinessException("La lista de unidades de medida no puede estar vacía.");
        }

        List<EntyPrvinvmdunidamedequipoDto> result = new ArrayList<>();

        for (EntyPrvinvmdunidamedequipoDto dto : dtos) {
            result.add(saveBefore(dto));
        }

        return result;
    }

    public EntyPrvinvmdunidamedequipoDto updateBefore(
            final Integer id,
            final EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {

        validarId(id);

        if (dto == null) {
            throw new EBusinessException("La unidad de medida no puede ser nula.");
        }

        normalizarDto(dto);
        aplicarDefaults(dto);
        validarEstadoRegistro(dto.getPrvEstadoregUnme());

        return dataProviders.update(id, dto);
    }

    public EntyPrvinvmdunidamedequipoDto updateByKey(
            final String unidadKey,
            final EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {

        validarTextoObligatorio(unidadKey, "La key de la unidad de medida es obligatoria.");

        if (dto == null) {
            throw new EBusinessException("La unidad de medida no puede ser nula.");
        }

        String key = unidadKey.trim().toUpperCase();

        dto.setPrvTipunidamedUnme(key);

        normalizarDto(dto);
        aplicarDefaults(dto);
        validarEstadoRegistro(dto.getPrvEstadoregUnme());

        return dataProviders.updateByKey(key, dto);
    }

    public String changestatus(
            final Integer id,
            final String estado
    ) throws EBusinessException {

        validarId(id);
        validarEstadoRegistro(estado);

        EntyPrvinvmdunidamedequipoDto dto = dataProviders.get(id);

        if (dto == null || dto.getPrvTipunidamedUnme() == null) {
            throw new EBusinessException("No existe la unidad de medida con id: " + id);
        }

        dto.setPrvEstadoregUnme(estado.trim());

        dataProviders.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String changestatusByKey(
            final String unidadKey,
            final String estado
    ) throws EBusinessException {

        validarTextoObligatorio(unidadKey, "La key de la unidad de medida es obligatoria.");
        validarEstadoRegistro(estado);

        String key = unidadKey.trim().toUpperCase();

        EntyPrvinvmdunidamedequipoDto dto = dataProviders.findByKey(key);

        if (dto == null || dto.getPrvTipunidamedUnme() == null) {
            throw new EBusinessException("No existe la unidad de medida con key: " + key);
        }

        dto.setPrvEstadoregUnme(estado.trim());

        dataProviders.updateByKey(key, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(
            final Integer id
    ) throws EBusinessException {

        validarId(id);
        dataProviders.delete(id);

        return "Registro eliminado correctamente";
    }

    public String deleteByKey(
            final String unidadKey
    ) throws EBusinessException {

        validarTextoObligatorio(unidadKey, "La key de la unidad de medida es obligatoria.");

        dataProviders.deleteByKey(unidadKey.trim().toUpperCase());

        return "Registro eliminado correctamente";
    }

    public EntyPrvinvmdunidamedequipoDto findByKey(
            final String unidadKey
    ) throws EBusinessException {

        validarTextoObligatorio(unidadKey, "La key de la unidad de medida es obligatoria.");

        return dataProviders.findByKey(unidadKey.trim().toUpperCase());
    }

    public List<EntyPrvinvmdunidamedequipoDto> findByEstado(
            final String estado
    ) throws EBusinessException {

        validarEstadoRegistro(estado);

        return dataProviders.findByEstado(estado.trim());
    }

    private void aplicarReglasCreacion(
            final EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw new EBusinessException("La unidad de medida no puede ser nula.");
        }

        normalizarDto(dto);
        aplicarDefaults(dto);
        validarObligatoriosCreacion(dto);
    }

    private void normalizarDto(
            final EntyPrvinvmdunidamedequipoDto dto
    ) {
        if (dto.getPrvTipunidamedUnme() != null) {
            dto.setPrvTipunidamedUnme(dto.getPrvTipunidamedUnme().trim().toUpperCase());
        }

        if (dto.getPrvDescmedidaUnme() != null) {
            dto.setPrvDescmedidaUnme(dto.getPrvDescmedidaUnme().trim().toUpperCase());
        }

        if (dto.getPrvEstadoregUnme() != null) {
            dto.setPrvEstadoregUnme(dto.getPrvEstadoregUnme().trim());
        }
    }

    private void aplicarDefaults(
            final EntyPrvinvmdunidamedequipoDto dto
    ) {
        if (esVacio(dto.getPrvDescmedidaUnme())) {
            dto.setPrvDescmedidaUnme("SIN DESCRIPCION");
        }

        if (esVacio(dto.getPrvEstadoregUnme())) {
            dto.setPrvEstadoregUnme(ESTADO_REGISTRO_ACTIVO);
        }
    }

    private void validarObligatoriosCreacion(
            final EntyPrvinvmdunidamedequipoDto dto
    ) throws EBusinessException {

        if (esVacio(dto.getPrvTipunidamedUnme())) {
            throw new EBusinessException("El código de la unidad de medida es obligatorio.");
        }

        validarEstadoRegistro(dto.getPrvEstadoregUnme());
    }

    private void validarId(
            final Integer id
    ) throws EBusinessException {
        if (id == null || id <= 0) {
            throw new EBusinessException("El id de la unidad de medida no es válido.");
        }
    }

    private void validarTextoObligatorio(
            final String valor,
            final String mensaje
    ) throws EBusinessException {
        if (esVacio(valor)) {
            throw new EBusinessException(mensaje);
        }
    }

    private void validarEstadoRegistro(
            final String estado
    ) throws EBusinessException {

        validarTextoObligatorio(estado, "El estado del registro es obligatorio.");

        String valor = estado.trim();

        if (!ESTADO_REGISTRO_ACTIVO.equals(valor)
                && !ESTADO_REGISTRO_INACTIVO.equals(valor)) {
            throw new EBusinessException("Estado de registro no válido. Use 1=Activo o 2=Inactivo.");
        }
    }

    private boolean esVacio(
            final String valor
    ) {
        return valor == null || valor.trim().isEmpty();
    }
}