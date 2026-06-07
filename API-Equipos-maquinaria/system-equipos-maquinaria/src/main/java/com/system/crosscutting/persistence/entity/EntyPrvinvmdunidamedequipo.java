package com.system.crosscutting.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidad JPA para la tabla prvinvmdunidamedequipo.
 *
 * Tabla maestra de unidades de medida de trabajo para equipos.
 */
@Getter
@Setter
@Entity
@Table(name = "prvinvmdunidamedequipo")
public class EntyPrvinvmdunidamedequipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "prv_tipunidamed_unme", length = 2, nullable = false)
    private String prvTipunidamedUnme;

    @Column(name = "prv_descmedida_unme", length = 40)
    private String prvDescmedidaUnme;

    @Column(name = "prv_estadoreg_unme", length = 1)
    private String prvEstadoregUnme;
}