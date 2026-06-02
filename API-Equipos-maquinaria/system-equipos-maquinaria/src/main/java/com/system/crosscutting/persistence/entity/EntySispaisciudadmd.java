package com.system.crosscutting.persistence.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sispaisciudadmd")
public class EntySispaisciudadmd implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sis_codpro_sipr")
    private String  sisCodproSipr;

    @Basic(optional = false)
    @Column(name = "sis_codmun_simu")
    private String  sisCodmunSimu;

    @Basic(optional = false)
    @Column(name = "sis_idedpt_sidp")
    private String  sisIdedptSidp;

    @Basic(optional = false)
    @Column(name = "sis_codpai_sipa")
    private String  sisCodpaiSipa;

    @Basic(optional = false)
    @Column(name = "sis_nombre_sipr")
    private String  sisNombreSipr;

    @Basic(optional = false)
    @Column(name = "sis_codpos_sipr")
    private String  sisCodposSipr;

    @Basic(optional = false)
    @Column(name = "sis_capita_sipr")
    private String  sisCapitaSipr;

    @Basic(optional = false)
    @Column(name = "sis_procla_sipr")
    private String  sisProclaSipr;

    @Basic(optional = false)
    @Column(name = "sis_geolat_sipr")
    private Float  sisGeolatSipr;

    @Basic(optional = false)
    @Column(name = "sis_geolon_sipr")
    private Float  sisGeolonSipr;

    @Basic(optional = false)
    @Column(name = "sis_counta_rkey")
    private Integer  sisCountaRkey;

    @Basic(optional = false)
    @Column(name = "sis_estreg_sipr")
    private String  sisEstregSipr;

}
