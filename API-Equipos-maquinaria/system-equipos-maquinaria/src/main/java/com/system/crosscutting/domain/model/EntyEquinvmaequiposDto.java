package com.system.crosscutting.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla EQUINVMAEQUIPOS.
 *
 * Se mantienen los mismos nombres de atributos de la entidad para permitir
 * conversión automática mediante GsonUtil y el patrón Translator.
 */
@Getter
@Setter
public class EntyEquinvmaequiposDto {

    /**
     * Código secuencial autoincremental del equipo.
     */
    private Integer equPrimarykeyEqui;

    /**
     * Código único funcional del equipo.
     */
    private String equIdentifkeyEqui;

    /**
     * Código único del tipo de equipo.
     */
    private String equIdentifkeyTieq;

    /**
     * Código único del proveedor propietario o suministrador.
     */
    private String prvIdentifkeyMprv;

    /**
     * Código interno del equipo.
     */
    private String equCodinternoEqui;

    /**
     * Nombre del equipo.
     */
    private String equNombreEqui;

    /**
     * Marca del equipo.
     */
    private String equMarcaEqui;

    /**
     * Modelo del equipo.
     */
    private String equModeloEqui;

    /**
     * Placa del equipo.
     */
    private String equPlacaEqui;

    /**
     * Serial del equipo.
     */
    private String equSerialEqui;

    /**
     * Estado operativo: 1=Disponible, 2=Asignado, 3=Mantenimiento, 4=Fuera de servicio.
     */
    private String equEstadooperEqui;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    private String equEstadoregEqui;
}
