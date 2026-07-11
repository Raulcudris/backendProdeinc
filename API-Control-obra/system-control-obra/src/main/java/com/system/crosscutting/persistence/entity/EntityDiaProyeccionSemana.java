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

@Getter
@Setter
@Entity
@Table(name = "ORSORDMDDIASPROYECSEMANA")
public class EntityDiaProyeccionSemana implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_DPSE")
    private Integer orsPrimarykeyDpse;

    @Column(name = "ORS_IDENTIFKEY_DPSE", length = 50, nullable = false)
    private String orsIdentifkeyDpse;

    @Column(name = "ORS_IDENTIFKEY_PSEM", length = 50, nullable = false)
    private String orsIdentifkeyPsem;

    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 50, nullable = false)
    private String orsIdentifkeyOrde;

    @Column(name = "ORS_FECHA_DPSE", nullable = false)
    private LocalDate orsFechaDpse;

    @Column(name = "ORS_NOMBREDIA_DPSE", length = 20, nullable = false)
    private String orsNombrediaDpse;

    @Column(name = "ORS_ESHABIL_DPSE", nullable = false)
    private Boolean orsEshabilDpse;

    @Column(name = "ORS_ESTRABAJADO_DPSE", nullable = false)
    private Boolean orsEstrabajadoDpse;

    @Column(name = "ORS_OBSERVACION_DPSE", length = 500)
    private String orsObservacionDpse;

    @Column(name = "ORS_TIPOREGIST_DPSE", length = 20)
    private String orsTiporegistDpse;

    @Column(name = "ORS_ESTADOREG_DPSE", length = 2, nullable = false)
    private String orsEstadoregDpse;
}