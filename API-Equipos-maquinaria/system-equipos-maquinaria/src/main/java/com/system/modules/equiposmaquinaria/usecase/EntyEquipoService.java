package com.system.modules.equiposmaquinaria.usecase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyPrvinvmainventarioequiposDto;
import com.system.crosscutting.domain.model.EntyPrvinvmainventarioequiposResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.dataproviders.IjpaEquipoDataProviders;

@Service
public class EntyEquipoService {

    private static final String ESTADO_REGISTRO_ACTIVO = "1";
    private static final String ESTADO_REGISTRO_INACTIVO = "2";

    private static final String EQUIPO_DISPONIBLE = "1";
    private static final String EQUIPO_NO_DISPONIBLE = "2";

    private static final String ESTADO_OPERATIVO = "OPE";

    @Autowired
    private IjpaEquipoDataProviders dataProviders;

    public EntyPrvinvmainventarioequiposResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyPrvinvmainventarioequiposResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyPrvinvmainventarioequiposDto get(
            final Integer id
    ) throws EBusinessException {

        validarId(id);
        return dataProviders.get(id);
    }

    public EntyPrvinvmainventarioequiposDto saveBefore(
            final EntyPrvinvmainventarioequiposDto dto
    ) throws EBusinessException {

        aplicarReglasCreacion(dto);
        return dataProviders.save(dto);
    }

    public List<EntyPrvinvmainventarioequiposDto> saveBefore(
            final List<EntyPrvinvmainventarioequiposDto> dtos
    ) throws EBusinessException {

        if (dtos == null || dtos.isEmpty()) {
            throw new EBusinessException("La lista de equipos no puede estar vacía.");
        }

        List<EntyPrvinvmainventarioequiposDto> result = new ArrayList<>();

        for (EntyPrvinvmainventarioequiposDto dto : dtos) {
            result.add(saveBefore(dto));
        }

        return result;
    }

    public EntyPrvinvmainventarioequiposDto updateBefore(
            final Integer id,
            final EntyPrvinvmainventarioequiposDto dto
    ) throws EBusinessException {

        validarId(id);

        if (dto == null) {
            throw new EBusinessException("El equipo no puede ser nulo.");
        }

        normalizarDto(dto);
        aplicarDefaultsUpdate(dto);

        return dataProviders.update(id, dto);
    }

    public String changestatus(
            final Integer id,
            final String estado
    ) throws EBusinessException {

        validarId(id);
        validarEstadoRegistro(estado);

        EntyPrvinvmainventarioequiposDto dto = dataProviders.get(id);

        if (dto == null || dto.getPrvPrimarykeyInve() == null) {
            throw new EBusinessException("No existe el equipo con id: " + id);
        }

        dto.setPrvEstadoregInve(estado.trim());

        dataProviders.update(id, dto);

        return "Estado actualizado correctamente";
    }

    public String cambiarDisponibilidad(
            final Integer id,
            final String disponible
    ) throws EBusinessException {

        validarId(id);
        validarDisponibilidad(disponible);

        EntyPrvinvmainventarioequiposDto dto = dataProviders.get(id);

        if (dto == null || dto.getPrvPrimarykeyInve() == null) {
            throw new EBusinessException("No existe el equipo con id: " + id);
        }

        dto.setPrvEquipoactivoInve(disponible.trim());

        dataProviders.update(id, dto);

        return "Disponibilidad actualizada correctamente";
    }

    public String deleteBefore(
            final Integer id
    ) throws EBusinessException {

        validarId(id);
        dataProviders.delete(id);

        return "Registro eliminado correctamente";
    }

    public EntyPrvinvmainventarioequiposDto findByKey(
            final String equipoKey
    ) throws EBusinessException {

        validarTextoObligatorio(equipoKey, "El código del equipo es obligatorio.");

        return dataProviders.findByKey(equipoKey.trim().toUpperCase());
    }

    public List<EntyPrvinvmainventarioequiposDto> findByProveedor(
            final String proveedorKey
    ) throws EBusinessException {

        validarTextoObligatorio(proveedorKey, "El código del proveedor es obligatorio.");

        return dataProviders.findByProveedor(proveedorKey.trim().toUpperCase());
    }

    public List<EntyPrvinvmainventarioequiposDto> findByTipoEquipo(
            final String tipoEquipoKey
    ) throws EBusinessException {

        validarTextoObligatorio(tipoEquipoKey, "El tipo de equipo es obligatorio.");

        return dataProviders.findByTipoEquipo(tipoEquipoKey.trim().toUpperCase());
    }

    public List<EntyPrvinvmainventarioequiposDto> findByDisponible(
            final String disponible
    ) throws EBusinessException {

        validarDisponibilidad(disponible);
        return dataProviders.findByDisponible(disponible.trim());
    }

    public List<EntyPrvinvmainventarioequiposDto> findByEstado(
            final String estado
    ) throws EBusinessException {

        validarEstadoRegistro(estado);
        return dataProviders.findByEstado(estado.trim());
    }

    private void aplicarReglasCreacion(
            final EntyPrvinvmainventarioequiposDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw new EBusinessException("El equipo no puede ser nulo.");
        }

        normalizarDto(dto);
        aplicarDefaultsCreacion(dto);
        validarObligatoriosCreacion(dto);
    }

    private void normalizarDto(
            final EntyPrvinvmainventarioequiposDto dto
    ) {
        if (dto.getPrvIdentifkeyInve() != null) {
            dto.setPrvIdentifkeyInve(dto.getPrvIdentifkeyInve().trim().toUpperCase());
        }

        if (dto.getPrvIdentifkeyMprv() != null) {
            dto.setPrvIdentifkeyMprv(dto.getPrvIdentifkeyMprv().trim().toUpperCase());
        }

        if (dto.getPrvTipoequipoTieq() != null) {
            dto.setPrvTipoequipoTieq(dto.getPrvTipoequipoTieq().trim().toUpperCase());
        }

        if (dto.getPrvNombrequipoInve() != null) {
            dto.setPrvNombrequipoInve(dto.getPrvNombrequipoInve().trim().toUpperCase());
        }

        if (dto.getPrvRefermodeloInve() != null) {
            dto.setPrvRefermodeloInve(dto.getPrvRefermodeloInve().trim().toUpperCase());
        }

        if (dto.getPrvEquipoestadoInve() != null) {
            dto.setPrvEquipoestadoInve(dto.getPrvEquipoestadoInve().trim().toUpperCase());
        }

        if (dto.getPrvEquipoactivoInve() != null) {
            dto.setPrvEquipoactivoInve(dto.getPrvEquipoactivoInve().trim());
        }

        if (dto.getPrvEstadoregInve() != null) {
            dto.setPrvEstadoregInve(dto.getPrvEstadoregInve().trim());
        }

        if (dto.getPrvDescripcionInve() != null) {
            dto.setPrvDescripcionInve(dto.getPrvDescripcionInve().trim());
        }
    }

    private void aplicarDefaultsCreacion(
            final EntyPrvinvmainventarioequiposDto dto
    ) {
        if (esVacio(dto.getPrvRefermodeloInve())) {
            dto.setPrvRefermodeloInve("SIN REFERENCIA");
        }

        if (esVacio(dto.getPrvEquipoestadoInve())) {
            dto.setPrvEquipoestadoInve(ESTADO_OPERATIVO);
        }

        if (esVacio(dto.getPrvEquipoactivoInve())) {
            dto.setPrvEquipoactivoInve(EQUIPO_DISPONIBLE);
        }

        if (esVacio(dto.getPrvEstadoregInve())) {
            dto.setPrvEstadoregInve(ESTADO_REGISTRO_ACTIVO);
        }

        if (esVacio(dto.getPrvDescripcionInve())) {
            dto.setPrvDescripcionInve("SIN DESCRIPCION");
        }
    }

    private void aplicarDefaultsUpdate(
            final EntyPrvinvmainventarioequiposDto dto
    ) {
        if (esVacio(dto.getPrvEquipoestadoInve())) {
            dto.setPrvEquipoestadoInve(ESTADO_OPERATIVO);
        }

        if (esVacio(dto.getPrvEquipoactivoInve())) {
            dto.setPrvEquipoactivoInve(EQUIPO_DISPONIBLE);
        }

        if (esVacio(dto.getPrvEstadoregInve())) {
            dto.setPrvEstadoregInve(ESTADO_REGISTRO_ACTIVO);
        }

        if (esVacio(dto.getPrvDescripcionInve())) {
            dto.setPrvDescripcionInve("SIN DESCRIPCION");
        }
    }

    private void validarObligatoriosCreacion(
            final EntyPrvinvmainventarioequiposDto dto
    ) throws EBusinessException {

        if (esVacio(dto.getPrvIdentifkeyInve())) {
            throw new EBusinessException("El código único del equipo es obligatorio.");
        }

        if (esVacio(dto.getPrvIdentifkeyMprv())) {
            throw new EBusinessException("El proveedor del equipo es obligatorio.");
        }

        if (esVacio(dto.getPrvTipoequipoTieq())) {
            throw new EBusinessException("El tipo de equipo es obligatorio.");
        }

        if (esVacio(dto.getPrvNombrequipoInve())) {
            throw new EBusinessException("El nombre del equipo es obligatorio.");
        }

        validarEstadoRegistro(dto.getPrvEstadoregInve());
        validarDisponibilidad(dto.getPrvEquipoactivoInve());
    }

    private void validarId(
            final Integer id
    ) throws EBusinessException {
        if (id == null || id <= 0) {
            throw new EBusinessException("El id del equipo no es válido.");
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

    private void validarDisponibilidad(
            final String disponible
    ) throws EBusinessException {
        validarTextoObligatorio(disponible, "La disponibilidad del equipo es obligatoria.");

        String valor = disponible.trim();

        if (!EQUIPO_DISPONIBLE.equals(valor)
                && !EQUIPO_NO_DISPONIBLE.equals(valor)) {
            throw new EBusinessException("Disponibilidad no válida. Use 1=Disponible o 2=No disponible.");
        }
    }

    private boolean esVacio(
            final String valor
    ) {
        return valor == null || valor.trim().isEmpty();
    }
}