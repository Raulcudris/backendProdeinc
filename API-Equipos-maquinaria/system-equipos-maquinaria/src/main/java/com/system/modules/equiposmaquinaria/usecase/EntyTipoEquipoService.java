package com.system.modules.equiposmaquinaria.usecase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyPrvinvmdequipmaquinariaDto;
import com.system.crosscutting.domain.model.EntyPrvinvmdequipmaquinariaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.dataproviders.IjpaTipoEquipoDataProviders;

@Service
public class EntyTipoEquipoService {

    private static final String ESTADO_REGISTRO_ACTIVO = "1";
    private static final String ESTADO_REGISTRO_INACTIVO = "2";
    private static final String TIPO_REGISTRO_ORIGINAL = "1";
    private static final String UNIDAD_DEFAULT = "HORA";

    @Autowired
    private IjpaTipoEquipoDataProviders dataProviders;

    public EntyPrvinvmdequipmaquinariaResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyPrvinvmdequipmaquinariaResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyPrvinvmdequipmaquinariaDto get(
            final Integer id
    ) throws EBusinessException {
        validarId(id);
        return dataProviders.get(id);
    }

    public EntyPrvinvmdequipmaquinariaDto saveBefore(
            final EntyPrvinvmdequipmaquinariaDto dto
    ) throws EBusinessException {

        aplicarReglasCreacion(dto);
        return dataProviders.save(dto);
    }

    public List<EntyPrvinvmdequipmaquinariaDto> saveBefore(
            final List<EntyPrvinvmdequipmaquinariaDto> dtos
    ) throws EBusinessException {

        if (dtos == null || dtos.isEmpty()) {
            throw new EBusinessException("La lista de tipos de equipo no puede estar vacía.");
        }

        List<EntyPrvinvmdequipmaquinariaDto> result = new ArrayList<>();

        for (EntyPrvinvmdequipmaquinariaDto dto : dtos) {
            result.add(saveBefore(dto));
        }

        return result;
    }

    public EntyPrvinvmdequipmaquinariaDto updateBefore(
            final Integer id,
            final EntyPrvinvmdequipmaquinariaDto dto
    ) throws EBusinessException {

        validarId(id);

        if (dto == null) {
            throw new EBusinessException("El tipo de equipo no puede ser nulo.");
        }

        normalizarDto(dto);
        aplicarDefaultsUpdate(dto);
        validarEstadoRegistro(dto.getPrvEstadoregTieq());

        return dataProviders.update(id, dto);
    }

    public String changestatus(
            final Integer id,
            final String estado
    ) throws EBusinessException {

        validarId(id);
        validarEstadoRegistro(estado);

        EntyPrvinvmdequipmaquinariaDto dto = dataProviders.get(id);

        if (dto == null || dto.getPrvPrimarykeyTieq() == null) {
            throw new EBusinessException("No existe el tipo de equipo con id: " + id);
        }

        dto.setPrvEstadoregTieq(estado.trim());

        dataProviders.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String deleteBefore(
            final Integer id
    ) throws EBusinessException {

        validarId(id);
        dataProviders.delete(id);

        return "Registro eliminado correctamente";
    }

    public EntyPrvinvmdequipmaquinariaDto findByKey(
            final String tipoEquipoKey
    ) throws EBusinessException {

        validarTextoObligatorio(tipoEquipoKey, "El código del tipo de equipo es obligatorio.");

        return dataProviders.findByKey(tipoEquipoKey.trim().toUpperCase());
    }

    public List<EntyPrvinvmdequipmaquinariaDto> findByUnidad(
            final String unidadKey
    ) throws EBusinessException {

        validarTextoObligatorio(unidadKey, "El código de la unidad de medida es obligatorio.");

        return dataProviders.findByUnidad(unidadKey.trim().toUpperCase());
    }

    public List<EntyPrvinvmdequipmaquinariaDto> findByEstado(
            final String estado
    ) throws EBusinessException {

        validarEstadoRegistro(estado);

        return dataProviders.findByEstado(estado.trim());
    }

    private void aplicarReglasCreacion(
            final EntyPrvinvmdequipmaquinariaDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw new EBusinessException("El tipo de equipo no puede ser nulo.");
        }

        normalizarDto(dto);
        aplicarDefaultsCreacion(dto);
        validarObligatoriosCreacion(dto);
    }

    private void normalizarDto(
            final EntyPrvinvmdequipmaquinariaDto dto
    ) {
        if (dto.getPrvTipoequipoTieq() != null) {
            dto.setPrvTipoequipoTieq(dto.getPrvTipoequipoTieq().trim().toUpperCase());
        }

        if (dto.getPrvIdentifkeyUnme() != null) {
            dto.setPrvIdentifkeyUnme(dto.getPrvIdentifkeyUnme().trim().toUpperCase());
        }

        if (dto.getPrvDescripcionTieq() != null) {
            dto.setPrvDescripcionTieq(dto.getPrvDescripcionTieq().trim().toUpperCase());
        }

        if (dto.getPrvTiporegistTieq() != null) {
            dto.setPrvTiporegistTieq(dto.getPrvTiporegistTieq().trim());
        }

        if (dto.getPrvEstadoregTieq() != null) {
            dto.setPrvEstadoregTieq(dto.getPrvEstadoregTieq().trim());
        }
    }

    private void aplicarDefaultsCreacion(
            final EntyPrvinvmdequipmaquinariaDto dto
    ) {
        if (esVacio(dto.getPrvDescripcionTieq())) {
            dto.setPrvDescripcionTieq("SIN DESCRIPCION");
        }

        if (esVacio(dto.getPrvIdentifkeyUnme())) {
            dto.setPrvIdentifkeyUnme(UNIDAD_DEFAULT);
        }

        if (esVacio(dto.getPrvTiporegistTieq())) {
            dto.setPrvTiporegistTieq(TIPO_REGISTRO_ORIGINAL);
        }

        if (esVacio(dto.getPrvEstadoregTieq())) {
            dto.setPrvEstadoregTieq(ESTADO_REGISTRO_ACTIVO);
        }
    }

    private void aplicarDefaultsUpdate(
            final EntyPrvinvmdequipmaquinariaDto dto
    ) {
        if (esVacio(dto.getPrvIdentifkeyUnme())) {
            dto.setPrvIdentifkeyUnme(UNIDAD_DEFAULT);
        }

        if (esVacio(dto.getPrvTiporegistTieq())) {
            dto.setPrvTiporegistTieq(TIPO_REGISTRO_ORIGINAL);
        }

        if (esVacio(dto.getPrvEstadoregTieq())) {
            dto.setPrvEstadoregTieq(ESTADO_REGISTRO_ACTIVO);
        }

        if (esVacio(dto.getPrvDescripcionTieq())) {
            dto.setPrvDescripcionTieq("SIN DESCRIPCION");
        }
    }

    private void validarObligatoriosCreacion(
            final EntyPrvinvmdequipmaquinariaDto dto
    ) throws EBusinessException {

        if (esVacio(dto.getPrvTipoequipoTieq())) {
            throw new EBusinessException("El código del tipo de equipo es obligatorio.");
        }

        if (esVacio(dto.getPrvIdentifkeyUnme())) {
            throw new EBusinessException("La unidad de medida del tipo de equipo es obligatoria.");
        }

        validarEstadoRegistro(dto.getPrvEstadoregTieq());
    }

    private void validarId(
            final Integer id
    ) throws EBusinessException {
        if (id == null || id <= 0) {
            throw new EBusinessException("El id del tipo de equipo no es válido.");
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