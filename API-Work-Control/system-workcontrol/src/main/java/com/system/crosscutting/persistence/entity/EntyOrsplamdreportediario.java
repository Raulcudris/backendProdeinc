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
@Table(name = "orsplamdreportediario")
public class EntyOrsplamdreportediario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ors_primarykey_pdia")
    private Integer orsPrimarykeyPdia;

    @Column(name = "ors_identifkey_pdia", length = 30, nullable = false)
    private String orsIdentifkeyPdia;

    @Column(name = "ors_identifkey_orde", length = 30)
    private String orsIdentifkeyOrde;

    @Column(name = "ors_identifkey_plse", length = 30)
    private String orsIdentifkeyPlse;

    @Column(name = "ors_identifkey_psem", length = 30)
    private String orsIdentifkeyPsem;

    @Column(name = "ors_observacion_pdia", length = 150)
    private String orsObservacionPdia;

    @Column(name = "ors_fechareport_pdia")
    private LocalDate orsFechareportPdia;

    @Column(name = "ors_ejecutunidad_pdia")
    private Integer orsEjecutunidadPdia;

    @Column(name = "ors_fechasistema_pdia")
    private LocalDate orsFechasistemaPdia;

    @Column(name = "ors_tiporegist_pdia", length = 2)
    private String orsTiporegistPdia;

    @Column(name = "ors_estadoreg_pdia", length = 1)
    private String orsEstadoregPdia;
}