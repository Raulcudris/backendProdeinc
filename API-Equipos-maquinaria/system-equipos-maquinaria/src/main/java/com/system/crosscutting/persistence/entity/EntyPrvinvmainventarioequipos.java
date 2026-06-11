package com.system.crosscutting.persistence.entity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prvinvmainventarioequipos")
public class EntyPrvinvmainventarioequipos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prv_primarykey_inve")
    private Integer prvPrimarykeyInve;

    @Column(name = "prv_identifkey_inve", nullable = false, length = 10)
    private String prvIdentifkeyInve;

    @Column(name = "prv_identifkey_mprv", length = 20)
    private String prvIdentifkeyMprv;

    @Column(name = "prv_tipoequipo_tieq", length = 5)
    private String prvTipoequipoTieq;

    @Column(name = "prv_nombrequipo_inve", length = 80)
    private String prvNombrequipoInve;

    @Column(name = "prv_refermodelo_inve", length = 50)
    private String prvRefermodeloInve;

    @Column(name = "prv_equipoestado_inve", length = 3)
    private String prvEquipoestadoInve;

    @Column(name = "prv_equipoactivo_inve", length = 1)
    private String prvEquipoactivoInve;

    @Column(name = "prv_estadoreg_inve", length = 1)
    private String prvEstadoregInve;

    @Column(name = "prv_descripcion_inve", length = 500)
    private String prvDescripcionInve;
}