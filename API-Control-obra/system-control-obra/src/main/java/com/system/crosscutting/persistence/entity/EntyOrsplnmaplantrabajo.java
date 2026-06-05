package com.system.crosscutting.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidad JPA que representa la tabla ORSPLNMAPLANTRABAJO.
 *
 * Esta tabla almacena los planes de trabajo proyectados asociados
 * a una orden de servicio y a un sitio o punto de trabajo.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSPLNMAPLANTRABAJO")
public class EntyOrsplnmaplantrabajo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_PLTR")
    private Integer orsPrimarykeyPltr;

    @Column(name = "ORS_IDENTIFKEY_PLTR", length = 30, nullable = false)
    private String orsIdentifkeyPltr;

    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 30, nullable = false)
    private String orsIdentifkeyOrde;

    @Column(name = "ORS_IDENTIFKEY_SITR", length = 30, nullable = false)
    private String orsIdentifkeySitr;

    @Column(name = "ORS_ACTIVIDAD_PLTR", length = 200)
    private String orsActividadPltr;

    @Column(name = "ORS_DESCRIPCION_PLTR", columnDefinition = "TEXT")
    private String orsDescripcionPltr;

    @Column(name = "ORS_UNIDADMEDIDA_PLTR", length = 20)
    private String orsUnidadmedidaPltr;

    @Column(name = "ORS_CANTIDADPROG_PLTR", precision = 17, scale = 2)
    private BigDecimal orsCantidadprogPltr;

    @Column(name = "ORS_FECHAINICIO_PLTR")
    private LocalDate orsFechainicioPltr;

    @Column(name = "ORS_FECHAFIN_PLTR")
    private LocalDate orsFechafinPltr;

    @Column(name = "ORS_ESTADOREG_PLTR", length = 1)
    private String orsEstadoregPltr;
}