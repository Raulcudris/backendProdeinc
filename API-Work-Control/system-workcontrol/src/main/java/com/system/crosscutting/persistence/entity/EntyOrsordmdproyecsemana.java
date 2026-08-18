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
@Table(name = "orsordmdproyecsemana")
public class EntyOrsordmdproyecsemana implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ors_primarykey_psem")
    private Integer orsPrimarykeyPsem;

    @Column(name = "ors_identifkey_psem", length = 30, nullable = false)
    private String orsIdentifkeyPsem;

    @Column(name = "ors_identifkey_orde", length = 30)
    private String orsIdentifkeyOrde;

    @Column(name = "ors_numerosem_psem")
    private Integer orsNumerosemPsem;

    @Column(name = "ors_titulosem_psem", length = 60)
    private String orsTitulosemPsem;

    @Column(name = "ors_semfechini_psem")
    private LocalDate orsSemfechiniPsem;

    @Column(name = "ors_semfechfin_psem")
    private LocalDate orsSemfechfinPsem;

    @Column(name = "ors_diashabiles_psem", length = 80)
    private String orsDiashabilesPsem;

    @Column(name = "ors_diasnhabiles_psem", length = 80)
    private String orsDiasnhabilesPsem;

    @Column(name = "ors_tiporegist_psem", length = 2)
    private String orsTiporegistPsem;

    @Column(name = "ors_estadoreg_psem", length = 1)
    private String orsEstadoregPsem;
}