package com.system.crosscutting.persistence.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidad JPA que representa la tabla ORSCONFNOVEDADHISTORI.
 *
 * Esta tabla almacena el historial de novedades o modificaciones
 * realizadas sobre registros base del sistema de control de obra.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSCONFNOVEDADHISTORI")
public class EntyOrsconfnovedadhistori implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código secuencial autoincremental generado por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_NOVE")
    private Integer orsPrimarykeyNove;

    /**
     * Código único funcional de la novedad.
     */
    @Column(name = "ORS_IDENTIFKEY_NOVE", length = 30, nullable = false)
    private String orsIdentifkeyNove;

    /**
     * Código único de la orden de servicio.
     */
    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 30)
    private String orsIdentifkeyOrde;

    /**
     * Fecha de registro de la novedad.
     */
    @Column(name = "ORS_FECHREPORT_NOVE")
    private LocalDate orsFechreportNove;

    /**
     * Tipo de novedad registrada.
     */
    @Column(name = "ORS_TIPONOVEDAD_NOVT", length = 40)
    private String orsTiponovedadNovt;

    /**
     * Código único del registro base modificado.
     */
    @Column(name = "ORS_REGISTRBASE_NOVE", length = 50)
    private String orsRegistrbaseNove;

    /**
     * Código único del nuevo registro generado por la novedad.
     */
    @Column(name = "ORS_REGISTRNOVE_NOVE", length = 50)
    private String orsRegistrnoveNove;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    @Column(name = "ORS_ESTADOREG_NOVE", length = 1)
    private String orsEstadoregNove;
}