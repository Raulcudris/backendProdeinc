package com.system.crosscutting.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidad JPA para la tabla prvinvmainventarioequipos.
 *
 * Tabla maestra de inventario de equipos del proveedor.
 */
@Getter
@Setter
@Entity
@Table(name = "prvinvmainventarioequipos")
public class EntyPrvinvmainventarioequipos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prv_primarykey_inve")
    private Integer prvPrimarykeyInve;

    @Column(name = "prv_identifkey_inve", length = 10, nullable = false)
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
}