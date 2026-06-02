package com.system.crosscutting.persistence.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Entidad JPA que representa la tabla DOCCATMACATEGORIA.
 *
 * Esta tabla almacena las categorías documentales del sistema, tales como
 * documentos legales, técnicos, contractuales y documentos de maquinaria.
 */
@Getter
@Setter
@Entity
@Table(name = "DOCCATMACATEGORIA")
public class EntyDoccatmacategoria {

    /**
     * Código secuencial autoincremental de categoría documental.
     */
    @Id
    @Column(name = "DOC_PRIMARYKEY_CADO")
    private Integer docPrimarykeyCado;

    /**
     * Código único de categoría documental.
     */
    @Column(name = "DOC_IDENTIFKEY_CADO", length = 20, nullable = false)
    private String docIdentifkeyCado;

    /**
     * Descripción de la categoría documental.
     */
    @Column(name = "DOC_DESCRIPCION_CADO", length = 150, nullable = false)
    private String docDescripcionCado;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    @Column(name = "DOC_ESTADOREG_CADO", length = 1, nullable = false)
    private String docEstadoregCado;
}