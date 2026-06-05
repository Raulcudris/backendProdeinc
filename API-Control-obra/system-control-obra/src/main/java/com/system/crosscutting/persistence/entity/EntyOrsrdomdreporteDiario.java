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
 * Entidad JPA que representa la tabla ORSRDOMDREPORTEDIARIO.
 *
 * Esta tabla almacena los reportes diarios o planillas diarias
 * de ejecución asociadas a una orden, plan de trabajo y plan semanal.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSRDOMDREPORTEDIARIO")
public class EntyOrsrdomdreporteDiario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_REDI")
    private Integer orsPrimarykeyRedi;

    @Column(name = "ORS_IDENTIFKEY_REDI", length = 30, nullable = false)
    private String orsIdentifkeyRedi;

    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 30, nullable = false)
    private String orsIdentifkeyOrde;

    @Column(name = "ORS_IDENTIFKEY_PLTR", length = 30, nullable = false)
    private String orsIdentifkeyPltr;

    @Column(name = "ORS_IDENTIFKEY_PSPL", length = 30)
    private String orsIdentifkeyPspl;

    @Column(name = "ORS_FECHAREPORTE_REDI")
    private LocalDate orsFechareporteRedi;

    @Column(name = "ORS_ACTIVIDAD_REDI", length = 200)
    private String orsActividadRedi;

    @Column(name = "ORS_CANTIDADPROG_REDI", precision = 17, scale = 2)
    private BigDecimal orsCantidadprogRedi;

    @Column(name = "ORS_CANTIDADEJEC_REDI", precision = 17, scale = 2)
    private BigDecimal orsCantidadejecRedi;

    @Column(name = "ORS_UNIDADMEDIDA_REDI", length = 20)
    private String orsUnidadmedidaRedi;

    @Column(name = "ORS_RESPONSABLE_REDI", length = 120)
    private String orsResponsableRedi;

    @Column(name = "ORS_OBSERVACION_REDI", columnDefinition = "TEXT")
    private String orsObservacionRedi;

    @Column(name = "ORS_ESTADOREG_REDI", length = 1)
    private String orsEstadoregRedi;
}