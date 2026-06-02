package com.system.crosscutting.persistence.entity;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sispaisbestados")
public class EntySispaisbestados {

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sis_idedpt_sidp")
    private String  sisIdedptSidp;

    @Basic(optional = false)
    @Column(name = "sis_coddpt_sidp")
    private String  sisCoddptSidp;

    @Basic(optional = false)
    @Column(name = "sis_codpai_sipa")
    private String  sisCodpaiSipa;

    @Basic(optional = false)
    @Column(name = "sis_nombre_sidp")
    private String  sisNombreSidp;

    @Basic(optional = false)
    @Column(name = "sis_secdet_sidp")
    private Integer  sisSecdetSidp;

    @Basic(optional = false)
    @Column(name = "sis_estreg_sidp")
    private Integer  sisEstregSidp;

}

