package com.system.crosscutting.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad JPA que representa la tabla EQUINVMAEQUIPOS.
 *
 * Esta tabla almacena el inventario maestro de equipos, maquinaria,
 * vehículos y herramientas disponibles para la ejecución de obras civiles.
 */
@Getter
@Setter
@Entity
@Table(name = "EQUINVMAEQUIPOS")
public class EntyEquinvmaequipos {

    /**
     * Código secuencial autoincremental del equipo.
     */
    @Id
    @Column(name = "EQU_PRIMARYKEY_EQUI")
    private Integer equPrimarykeyEqui;

    /**
     * Código único funcional del equipo.
     */
    @Column(name = "EQU_IDENTIFKEY_EQUI", length = 30, nullable = false)
    private String equIdentifkeyEqui;

    /**
     * Código único del tipo de equipo.
     */
    @Column(name = "EQU_IDENTIFKEY_TIEQ", length = 20, nullable = false)
    private String equIdentifkeyTieq;

    /**
     * Código único del proveedor propietario o suministrador del equipo.
     */
    @Column(name = "PRV_IDENTIFKEY_MPRV", length = 20)
    private String prvIdentifkeyMprv;

    /**
     * Código interno del equipo.
     */
    @Column(name = "EQU_CODINTERNO_EQUI", length = 30)
    private String equCodinternoEqui;

    /**
     * Nombre del equipo.
     */
    @Column(name = "EQU_NOMBRE_EQUI", length = 120, nullable = false)
    private String equNombreEqui;

    /**
     * Marca del equipo.
     */
    @Column(name = "EQU_MARCA_EQUI", length = 80)
    private String equMarcaEqui;

    /**
     * Modelo del equipo.
     */
    @Column(name = "EQU_MODELO_EQUI", length = 80)
    private String equModeloEqui;

    /**
     * Placa del equipo, si aplica.
     */
    @Column(name = "EQU_PLACA_EQUI", length = 30)
    private String equPlacaEqui;

    /**
     * Serial del equipo.
     */
    @Column(name = "EQU_SERIAL_EQUI", length = 80)
    private String equSerialEqui;

    /**
     * Estado operativo: 1=Disponible, 2=Asignado, 3=Mantenimiento, 4=Fuera de servicio.
     */
    @Column(name = "EQU_ESTADOOPER_EQUI", length = 1, nullable = false)
    private String equEstadooperEqui;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    @Column(name = "EQU_ESTADOREG_EQUI", length = 1, nullable = false)
    private String equEstadoregEqui;
}