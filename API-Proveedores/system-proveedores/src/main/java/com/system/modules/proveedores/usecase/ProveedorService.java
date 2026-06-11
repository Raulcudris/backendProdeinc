package com.system.modules.proveedores.usecase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaDto;
import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.proveedores.dataproviders.IjpaProveedorDataProviders;

@Service
public class ProveedorService {

    private static final String ESTADO_REGISTRO_ACTIVO = "1";
    private static final String ESTADO_REGISTRO_INACTIVO = "2";

    private static final String SIN_DATO = "SIN DATO";
    private static final String PAIS_COLOMBIA_DEFAULT = "CO";

    @Autowired
    private IjpaProveedorDataProviders dataProviders;

    public EntyPrvmaeproveedoresmaResponse getAll()
            throws EBusinessException {
        return dataProviders.getAll();
    }

    public EntyPrvmaeproveedoresmaResponse getAll(
            final int currentPage,
            final int pageSize,
            final String parameter,
            final String filter
    ) throws EBusinessException {
        return dataProviders.getAll(currentPage, pageSize, parameter, filter);
    }

    public EntyPrvmaeproveedoresmaDto get(
            final Integer id
    ) throws EBusinessException {

        validarId(id);
        return dataProviders.get(id);
    }

    public EntyPrvmaeproveedoresmaDto saveBefore(
            final EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {

        aplicarReglasCreacion(dto);
        return dataProviders.save(dto);
    }

    public List<EntyPrvmaeproveedoresmaDto> saveBefore(
            final List<EntyPrvmaeproveedoresmaDto> dtos
    ) throws EBusinessException {

        if (dtos == null || dtos.isEmpty()) {
            throw new EBusinessException("La lista de proveedores no puede estar vacía.");
        }

        List<EntyPrvmaeproveedoresmaDto> result = new ArrayList<>();

        for (EntyPrvmaeproveedoresmaDto dto : dtos) {
            result.add(saveBefore(dto));
        }

        return result;
    }

    public EntyPrvmaeproveedoresmaDto updateBefore(
            final Integer id,
            final EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {

        validarId(id);

        if (dto == null) {
            throw new EBusinessException("El proveedor no puede ser nulo.");
        }

        normalizarDto(dto);
        aplicarDefaults(dto);
        validarEstadoRegistro(dto.getPrvEstadoregMprv());

        return dataProviders.update(id, dto);
    }

    public String changestatus(
            final Integer id,
            final String estado
    ) throws EBusinessException {

        validarId(id);
        validarEstadoRegistro(estado);

        EntyPrvmaeproveedoresmaDto dto = dataProviders.get(id);

        if (dto == null || dto.getPrvPrimarykeyMprv() == null) {
            throw new EBusinessException("No existe el proveedor con id: " + id);
        }

        dto.setPrvEstadoregMprv(estado.trim());

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

    public EntyPrvmaeproveedoresmaDto findByKey(
            final String proveedorKey
    ) throws EBusinessException {

        validarTextoObligatorio(proveedorKey, "El código del proveedor es obligatorio.");

        return dataProviders.findByKey(proveedorKey.trim().toUpperCase());
    }

    public EntyPrvmaeproveedoresmaDto findByNit(
            final String numeroNit
    ) throws EBusinessException {

        validarTextoObligatorio(numeroNit, "El NIT del proveedor es obligatorio.");

        return dataProviders.findByNit(numeroNit.trim());
    }

    public List<EntyPrvmaeproveedoresmaDto> findByEstado(
            final String estado
    ) throws EBusinessException {

        validarEstadoRegistro(estado);

        return dataProviders.findByEstado(estado.trim());
    }

    private void aplicarReglasCreacion(
            final EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {

        if (dto == null) {
            throw new EBusinessException("El proveedor no puede ser nulo.");
        }

        normalizarDto(dto);
        aplicarDefaults(dto);
        validarObligatoriosCreacion(dto);
    }

    private void normalizarDto(
            final EntyPrvmaeproveedoresmaDto dto
    ) {
        if (dto.getPrvIdentifkeyMprv() != null) {
            dto.setPrvIdentifkeyMprv(dto.getPrvIdentifkeyMprv().trim().toUpperCase());
        }

        if (dto.getPrvNumeronitMprv() != null) {
            dto.setPrvNumeronitMprv(dto.getPrvNumeronitMprv().trim());
        }

        if (dto.getPrvRazonsocialMprv() != null) {
            dto.setPrvRazonsocialMprv(dto.getPrvRazonsocialMprv().trim().toUpperCase());
        }

        if (dto.getPrvObjetosocialMprv() != null) {
            dto.setPrvObjetosocialMprv(dto.getPrvObjetosocialMprv().trim().toUpperCase());
        }

        if (dto.getSisTiposociedadTpso() != null) {
            dto.setSisTiposociedadTpso(dto.getSisTiposociedadTpso().trim().toUpperCase());
        }

        if (dto.getSisCodactividadCiiu() != null) {
            dto.setSisCodactividadCiiu(dto.getSisCodactividadCiiu().trim());
        }

        if (dto.getPrvPaginawebMprv() != null) {
            dto.setPrvPaginawebMprv(dto.getPrvPaginawebMprv().trim().toLowerCase());
        }

        if (dto.getPrvDireccionMprv() != null) {
            dto.setPrvDireccionMprv(dto.getPrvDireccionMprv().trim().toUpperCase());
        }

        if (dto.getPrvTelefonoMprv() != null) {
            dto.setPrvTelefonoMprv(dto.getPrvTelefonoMprv().trim());
        }

        if (dto.getPrvCorreoMprv() != null) {
            dto.setPrvCorreoMprv(dto.getPrvCorreoMprv().trim().toLowerCase());
        }

        if (dto.getSisCodpaiSipa() != null) {
            dto.setSisCodpaiSipa(dto.getSisCodpaiSipa().trim().toUpperCase());
        }

        if (dto.getSisIdedptSidp() != null) {
            dto.setSisIdedptSidp(dto.getSisIdedptSidp().trim().toUpperCase());
        }

        if (dto.getSisCodproSipr() != null) {
            dto.setSisCodproSipr(dto.getSisCodproSipr().trim().toUpperCase());
        }

        if (dto.getPrvCodposMprv() != null) {
            dto.setPrvCodposMprv(dto.getPrvCodposMprv().trim());
        }

        if (dto.getPrvIdentifkeyRelg() != null) {
            dto.setPrvIdentifkeyRelg(dto.getPrvIdentifkeyRelg().trim().toUpperCase());
        }

        if (dto.getPrvEstadoregMprv() != null) {
            dto.setPrvEstadoregMprv(dto.getPrvEstadoregMprv().trim());
        }
    }

    private void aplicarDefaults(
            final EntyPrvmaeproveedoresmaDto dto
    ) {
        if (esVacio(dto.getPrvEstadoregMprv())) {
            dto.setPrvEstadoregMprv(ESTADO_REGISTRO_ACTIVO);
        }

        if (esVacio(dto.getPrvObjetosocialMprv())) {
            dto.setPrvObjetosocialMprv(SIN_DATO);
        }

        if (esVacio(dto.getSisTiposociedadTpso())) {
            dto.setSisTiposociedadTpso(SIN_DATO);
        }

        if (esVacio(dto.getSisCodactividadCiiu())) {
            dto.setSisCodactividadCiiu(SIN_DATO);
        }

        if (esVacio(dto.getPrvPaginawebMprv())) {
            dto.setPrvPaginawebMprv("sin-pagina-web.local");
        }

        if (esVacio(dto.getPrvDireccionMprv())) {
            dto.setPrvDireccionMprv("SIN DIRECCION");
        }

        if (esVacio(dto.getPrvTelefonoMprv())) {
            dto.setPrvTelefonoMprv("SIN TELEFONO");
        }

        if (esVacio(dto.getPrvCorreoMprv())) {
            dto.setPrvCorreoMprv("sin-correo@proveedor.local");
        }

        if (esVacio(dto.getSisCodpaiSipa())) {
            dto.setSisCodpaiSipa(PAIS_COLOMBIA_DEFAULT);
        }

        if (esVacio(dto.getSisIdedptSidp())) {
            dto.setSisIdedptSidp(SIN_DATO);
        }

        if (esVacio(dto.getSisCodproSipr())) {
            dto.setSisCodproSipr(SIN_DATO);
        }

        if (esVacio(dto.getPrvCodposMprv())) {
            dto.setPrvCodposMprv(SIN_DATO);
        }

        if (esVacio(dto.getPrvIdentifkeyRelg())) {
            dto.setPrvIdentifkeyRelg(SIN_DATO);
        }
    }

    private void validarObligatoriosCreacion(
            final EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {

        if (esVacio(dto.getPrvIdentifkeyMprv())) {
            throw new EBusinessException("El código único del proveedor es obligatorio.");
        }

        if (esVacio(dto.getPrvNumeronitMprv())) {
            throw new EBusinessException("El NIT del proveedor es obligatorio.");
        }

        if (esVacio(dto.getPrvRazonsocialMprv())) {
            throw new EBusinessException("La razón social del proveedor es obligatoria.");
        }

        validarEstadoRegistro(dto.getPrvEstadoregMprv());
    }

    private void validarId(
            final Integer id
    ) throws EBusinessException {
        if (id == null || id <= 0) {
            throw new EBusinessException("El id del proveedor no es válido.");
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