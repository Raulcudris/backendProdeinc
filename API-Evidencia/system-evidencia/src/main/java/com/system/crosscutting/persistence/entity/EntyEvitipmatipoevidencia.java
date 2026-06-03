package com.system.crosscutting.persistence.entity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad JPA que representa la tabla EVITIPMATIPOEVIDENCIA.
 *
 * Esta tabla almacena los tipos de evidencia permitidos por el sistema:
 * fotografía, video, documento, audio o archivo general.
 */
@Getter
@Setter
@Entity
@Table(name = "EVITIPMATIPOEVIDENCIA")
public class EntyEvitipmatipoevidencia {

    /**
     * Código secuencial autoincremental del tipo de evidencia.
     */
    @Id
    @Column(name = "EVI_PRIMARYKEY_TIEV")
    private Integer eviPrimarykeyTiev;

    /**
     * Código único funcional del tipo de evidencia.
     */
    @Column(name = "EVI_IDENTIFKEY_TIEV", length = 20, nullable = false)
    private String eviIdentifkeyTiev;

    /**
     * Descripción del tipo de evidencia.
     */
    @Column(name = "EVI_DESCRIPCION_TIEV", length = 120, nullable = false)
    private String eviDescripcionTiev;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    @Column(name = "EVI_ESTADOREG_TIEV", length = 1, nullable = false)
    private String eviEstadoregTiev;
}