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
 * Entidad JPA que representa la tabla ORSPLAMDREPORTEDIARIO.
 *
 * Esta tabla almacena los reportes diarios de ejecución
 * asociados a un plan semanal de trabajo.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSPLAMDREPORTEDIARIO")
public class EntyOrsplamdreportediario implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código secuencial autoincremental generado por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_PDIA")
    private Integer orsPrimarykeyPdia;

    /**
     * Código único funcional del reporte diario.
     */
    @Column(name = "ORS_IDENTIFKEY_PDIA", length = 30, nullable = false)
    private String orsIdentifkeyPdia;

    /**
     * Código único de la orden de servicio.
     */
    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 30)
    private String orsIdentifkeyOrde;

    /**
     * Código único del plan semanal.
     */
    @Column(name = "ORS_IDENTIFKEY_PLSE", length = 30)
    private String orsIdentifkeyPlse;

    /**
     * Código único de la proyección semanal.
     */
    @Column(name = "ORS_IDENTIFKEY_PSEM", length = 30)
    private String orsIdentifkeyPsem;

    /**
     * Observación del reporte diario.
     */
    @Column(name = "ORS_OBSERVACION_PDIA", length = 150)
    private String orsObservacionPdia;

    /**
     * Fecha del reporte diario.
     */
    @Column(name = "ORS_FECHAREPORT_PDIA")
    private LocalDate orsFechareportPdia;

    /**
     * Cantidad ejecutada en el día.
     */
    @Column(name = "ORS_EJECUTUNIDAD_PDIA")
    private Integer orsEjecutunidadPdia;

    /**
     * Fecha del sistema en la que se registró el reporte.
     */
    @Column(name = "ORS_FECHASISTEMA_PDIA")
    private LocalDate orsFechasistemaPdia;

    /**
     * Tipo de registro: 1=Registro original, 2=Novedad.
     */
    @Column(name = "ORS_TIPOREGIST_PDIA", length = 2)
    private String orsTiporegistPdia;

    /**
     * Estado del registro: 1=Abierto, 2=Cerrado, 3=Cancelado.
     */
    @Column(name = "ORS_ESTADOREG_PDIA", length = 1)
    private String orsEstadoregPdia;
}