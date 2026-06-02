package com.system.crosscutting.persistence.entity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad JPA que representa la tabla EQUMEDMAUNIDADMEDIDA.
 *
 * Esta tabla almacena las unidades de medida usadas en los planes de trabajo,
 * reportes diarios y control de ejecución de obra.
 */
@Getter
@Setter
@Entity
@Table(name = "EQUMEDMAUNIDADMEDIDA")
public class EntyEqumedmaunidadmedida {

    /**
     * Código secuencial autoincremental de unidad de medida.
     */
    @Id
    @Column(name = "EQU_PRIMARYKEY_UNME")
    private Integer equPrimarykeyUnme;

    /**
     * Código único de unidad de medida.
     */
    @Column(name = "EQU_IDENTIFKEY_UNME", length = 20, nullable = false)
    private String equIdentifkeyUnme;

    /**
     * Código corto de unidad de medida.
     */
    @Column(name = "EQU_CODIGO_UNME", length = 10, nullable = false)
    private String equCodigoUnme;

    /**
     * Descripción de la unidad de medida.
     */
    @Column(name = "EQU_DESCRIPCION_UNME", length = 100, nullable = false)
    private String equDescripcionUnme;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    @Column(name = "EQU_ESTADOREG_UNME", length = 1, nullable = false)
    private String equEstadoregUnme;
}
