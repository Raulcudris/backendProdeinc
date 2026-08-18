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

@Getter
@Setter
@Entity
@Table(name = "ORSORDMAORDENSERVICIO")
public class EntyOrsordmaordenservicio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_ORDE")
    private Integer orsPrimarykeyOrde;

    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 50, nullable = false)
    private String orsIdentifkeyOrde;

    @Column(name = "ORS_AUTORIFECHA_ORDE")
    private LocalDate orsAutorifechaOrde;

    @Column(name = "ORS_CODSERVICIO_SEBS", length = 50)
    private String orsCodservicioSebs;

    @Column(name = "ORS_SERVICEVENT_ORDE", length = 200)
    private String orsServiceventOrde;

    @Column(name = "ORS_SERVICLUGAR_ORDE", length = 250)
    private String orsServiclugarOrde;

    @Column(name = "ORS_SERVICOBJETO_ORDE", length = 1000)
    private String orsServicobjetoOrde;

    @Column(name = "ORS_PLANFECHINI_ORDE")
    private LocalDate orsPlanfechiniOrde;

    @Column(name = "ORS_PLANFECHFIN_ORDE")
    private LocalDate orsPlanfechfinOrde;

    @Column(name = "PRV_IDENTIFKEY_MPRV", length = 50)
    private String prvIdentifkeyMprv;

    @Column(name = "PRV_IDENTIFKEY_RELG", length = 50)
    private String prvIdentifkeyRelg;

    @Column(name = "ORS_VALORBASE_ORDE", precision = 18, scale = 2)
    private BigDecimal orsValorbaseOrde;

    @Column(name = "ORS_VALORDEIVA_ORDE", precision = 18, scale = 2)
    private BigDecimal orsValordeivaOrde;

    @Column(name = "ORS_VALORTOTAL_ORDE", precision = 18, scale = 2)
    private BigDecimal orsValortotalOrde;

    @Column(name = "ORS_TIPOREGIST_ORDE", length = 2)
    private String orsTiporegistOrde;

    @Column(name = "ORS_ESTADOREG_ORDE", length = 2)
    private String orsEstadoregOrde;
}