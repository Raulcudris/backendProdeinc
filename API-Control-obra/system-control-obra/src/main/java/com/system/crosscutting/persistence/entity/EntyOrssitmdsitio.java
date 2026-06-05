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
 * Entidad JPA que representa la tabla ORSSITMDSITIO.
 *
 * Esta tabla almacena los sitios, frentes o puntos de trabajo
 * asociados a una orden de servicio.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSSITMDSITIO")
public class EntyOrssitmdsitio implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código secuencial autoincremental generado por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_SITR")
    private Integer orsPrimarykeySitr;

    /**
     * Código único funcional del sitio o punto de trabajo.
     */
    @Column(name = "ORS_IDENTIFKEY_SITR", length = 30, nullable = false)
    private String orsIdentifkeySitr;

    /**
     * Código funcional de la orden de servicio asociada.
     */
    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 30, nullable = false)
    private String orsIdentifkeyOrde;

    /**
     * Nombre del sitio o punto de trabajo.
     */
    @Column(name = "ORS_NOMBRE_SITR", length = 150)
    private String orsNombreSitr;

    /**
     * Descripción del sitio o punto de trabajo.
     */
    @Column(name = "ORS_DESCRIPCION_SITR", columnDefinition = "TEXT")
    private String orsDescripcionSitr;

    /**
     * Ubicación textual del sitio.
     */
    @Column(name = "ORS_UBICACION_SITR", length = 200)
    private String orsUbicacionSitr;

    /**
     * Latitud del sitio.
     */
    @Column(name = "ORS_LATITUD_SITR")
    private Float orsLatitudSitr;

    /**
     * Longitud del sitio.
     */
    @Column(name = "ORS_LONGITUD_SITR")
    private Float orsLongitudSitr;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    @Column(name = "ORS_ESTADOREG_SITR", length = 1)
    private String orsEstadoregSitr;
}