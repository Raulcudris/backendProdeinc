package com.system.crosscutting.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * Entidad JPA que representa la tabla ORSPSPMDPLANSEMANAL.
 *
 * Esta tabla almacena la proyección semanal de ejecución asociada
 * a un plan de trabajo proyectado.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSPSPMDPLANSEMANAL")
public class EntyOrspspmdplansemanal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_PSPL")
    private Integer orsPrimarykeyPspl;

    @Column(name = "ORS_IDENTIFKEY_PSPL", length = 30, nullable = false)
    private String orsIdentifkeyPspl;

    @Column(name = "ORS_IDENTIFKEY_PLTR", length = 30, nullable = false)
    private String orsIdentifkeyPltr;

    @Column(name = "ORS_SEMANA_PSPL")
    private Integer orsSemanaPspl;

    @Column(name = "ORS_FECHAINICIO_PSPL")
    private LocalDate orsFechainicioPspl;

    @Column(name = "ORS_FECHAFIN_PSPL")
    private LocalDate orsFechafinPspl;

    @Column(name = "ORS_CANTIDADPROG_PSPL", precision = 17, scale = 2)
    private BigDecimal orsCantidadprogPspl;

    @Column(name = "ORS_OBSERVACION_PSPL", columnDefinition = "TEXT")
    private String orsObservacionPspl;

    @Column(name = "ORS_ESTADOREG_PSPL", length = 1)
    private String orsEstadoregPspl;
}