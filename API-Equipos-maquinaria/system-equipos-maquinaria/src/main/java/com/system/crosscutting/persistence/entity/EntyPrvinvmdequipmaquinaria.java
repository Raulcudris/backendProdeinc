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
 * Entidad JPA para la tabla prvinvmdequipmaquinaria.
 *
 * Tabla maestra de tipos de equipo o maquinaria.
 */
@Getter
@Setter
@Entity
@Table(name = "prvinvmdequipmaquinaria")
public class EntyPrvinvmdequipmaquinaria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prv_primarykey_tieq")
    private Integer prvPrimarykeyTieq;

    @Column(name = "prv_tipoequipo_tieq", length = 5, nullable = false)
    private String prvTipoequipoTieq;

    @Column(name = "prv_desequipo_tieq", length = 80)
    private String prvDesequipoTieq;

    @Column(name = "prv_tipunidamed_unme", length = 2)
    private String prvTipunidamedUnme;

    @Column(name = "prv_estadoreg_tieq", length = 1)
    private String prvEstadoregTieq;
}