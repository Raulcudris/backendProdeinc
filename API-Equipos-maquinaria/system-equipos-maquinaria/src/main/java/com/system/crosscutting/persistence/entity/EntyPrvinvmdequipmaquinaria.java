package com.system.crosscutting.persistence.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prvinvmdequipmaquinaria")
public class EntyPrvinvmdequipmaquinaria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prv_primarykey_tieq")
    private Integer prvPrimarykeyTieq;

    @Column(name = "prv_tipoequipo_tieq", nullable = false, unique = true, length = 30)
    private String prvTipoequipoTieq;

    @Column(name = "prv_descripcion_tieq", length = 150)
    private String prvDescripcionTieq;

    @Column(name = "prv_identifkey_unme", length = 30)
    private String prvIdentifkeyUnme;

    @Column(name = "prv_tiporegist_tieq", length = 2)
    private String prvTiporegistTieq;

    @Column(name = "prv_estadoreg_tieq", length = 2)
    private String prvEstadoregTieq;
}