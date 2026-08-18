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
@Table(name = "orsconfnovedadhistori")
public class EntyOrsconfnovedadhistori implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ors_primarykey_nove")
    private Integer orsPrimarykeyNove;

    @Column(name = "ors_identifkey_nove", length = 30, nullable = false)
    private String orsIdentifkeyNove;

    @Column(name = "ors_identifkey_orde", length = 30)
    private String orsIdentifkeyOrde;

    @Column(name = "ors_fechreport_nove")
    private LocalDate orsFechreportNove;

    @Column(name = "ors_tiponovedad_novt", length = 40)
    private String orsTiponovedadNovt;

    @Column(name = "ors_registrbase_nove", length = 50)
    private String orsRegistrbaseNove;

    @Column(name = "ors_registrnove_nove", length = 50)
    private String orsRegistrnoveNove;

    @Column(name = "ors_estadoreg_nove", length = 1)
    private String orsEstadoregNove;
}