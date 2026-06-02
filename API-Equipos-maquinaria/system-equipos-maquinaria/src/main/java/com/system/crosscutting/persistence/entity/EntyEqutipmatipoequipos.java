package com.system.crosscutting.persistence.entity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad JPA que representa la tabla EQUTIPMATIPOEQUIPO.
 *
 * Esta tabla almacena los tipos de equipos, maquinaria, vehículos o herramientas
 * utilizados en la ejecución de obras civiles.
 */
@Getter
@Setter
@Entity
@Table(name = "EQUTIPMATIPOEQUIPO")
public class EntyEqutipmatipoequipos {

    /**
     * Código secuencial autoincremental del tipo de equipo.
     */
    @Id
    @Column(name = "EQU_PRIMARYKEY_TIEQ")
    private Integer equPrimarykeyTieq;

    /**
     * Código único del tipo de equipo.
     */
    @Column(name = "EQU_IDENTIFKEY_TIEQ", length = 20, nullable = false)
    private String equIdentifkeyTieq;

    /**
     * Descripción del tipo de equipo.
     */
    @Column(name = "EQU_DESCRIPCION_TIEQ", length = 120, nullable = false)
    private String equDescripcionTieq;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    @Column(name = "EQU_ESTADOREG_TIEQ", length = 1, nullable = false)
    private String equEstadoregTieq;
}
