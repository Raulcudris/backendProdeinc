package com.system.crosscutting.persistence.entity;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad JPA que representa la tabla EQUASIMDASIGNEQUIPO.
 *
 * Esta tabla almacena las asignaciones de equipos, maquinaria, vehículos o herramientas
 * a órdenes de servicio, planes de trabajo y responsables operativos.
 */
@Getter
@Setter
@Entity
@Table(name = "EQUASIMDASIGNEQUIPO")
public class EntyEquasimdasignequipo {

    /**
     * Código secuencial autoincremental de asignación de equipo.
     */
    @Id
    @Column(name = "EQU_PRIMARYKEY_ASEQ")
    private Integer equPrimarykeyAseq;

    /**
     * Código único funcional de asignación de equipo.
     */
    @Column(name = "EQU_IDENTIFKEY_ASEQ", length = 30, nullable = false)
    private String equIdentifkeyAseq;

    /**
     * Código único funcional del equipo asignado.
     */
    @Column(name = "EQU_IDENTIFKEY_EQUI", length = 30, nullable = false)
    private String equIdentifkeyEqui;

    /**
     * Código único funcional de la orden de servicio.
     */
    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 30)
    private String orsIdentifkeyOrde;

    /**
     * Código único funcional del plan de trabajo.
     */
    @Column(name = "ORS_IDENTIFKEY_PLTR", length = 30)
    private String orsIdentifkeyPltr;

    /**
     * Responsable u operador asignado al equipo.
     */
    @Column(name = "EQU_RESPONSABLE_ASEQ", length = 120)
    private String equResponsableAseq;

    /**
     * Fecha de asignación del equipo.
     */
    @Column(name = "EQU_FECHAASIG_ASEQ", nullable = false)
    private LocalDate equFechaasigAseq;

    /**
     * Fecha de devolución del equipo.
     */
    @Column(name = "EQU_FECHADEVOL_ASEQ")
    private LocalDate equFechadevolAseq;

    /**
     * Observaciones de la asignación.
     */
    @Column(name = "EQU_OBSERVACION_ASEQ", columnDefinition = "TEXT")
    private String equObservacionAseq;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo, 3=Cerrado.
     */
    @Column(name = "EQU_ESTADOREG_ASEQ", length = 1, nullable = false)
    private String equEstadoregAseq;
}
