package com.system.crosscutting.persistence.entity;

import java.time.LocalDate;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prvmaeproveedoresma")
public class EntyPrvmaeproveedoresma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prv_primarykey_mprv")
    private Integer prvPrimarykeyMprv;

    @Column(name = "prv_identifkey_mprv", nullable = false, unique = true, length = 30)
    private String prvIdentifkeyMprv;

    @Column(name = "prv_numeronit_mprv", nullable = false, length = 30)
    private String prvNumeronitMprv;

    @Column(name = "prv_razonsocial_mprv", nullable = false, length = 200)
    private String prvRazonsocialMprv;

    @Column(name = "prv_objetosocial_mprv", length = 500)
    private String prvObjetosocialMprv;

    @Column(name = "sis_tiposociedad_tpso", length = 30)
    private String sisTiposociedadTpso;

    @Column(name = "sis_codactividad_ciiu", length = 30)
    private String sisCodactividadCiiu;

    @Column(name = "prv_fechconst_mprv")
    private LocalDate prvFechconstMprv;

    @Column(name = "prv_paginaweb_mprv", length = 200)
    private String prvPaginawebMprv;

    @Column(name = "prv_direccion_mprv", length = 250)
    private String prvDireccionMprv;

    @Column(name = "prv_telefono_mprv", length = 50)
    private String prvTelefonoMprv;

    @Column(name = "prv_correo_mprv", length = 150)
    private String prvCorreoMprv;

    @Column(name = "sis_codpai_sipa", length = 30)
    private String sisCodpaiSipa;

    @Column(name = "sis_idedpt_sidp", length = 30)
    private String sisIdedptSidp;

    @Column(name = "sis_codpro_sipr", length = 30)
    private String sisCodproSipr;

    @Column(name = "prv_codpos_mprv", length = 30)
    private String prvCodposMprv;

    @Column(name = "prv_identifkey_relg", length = 30)
    private String prvIdentifkeyRelg;

    @Column(name = "prv_estadoreg_mprv", length = 2)
    private String prvEstadoregMprv;
}