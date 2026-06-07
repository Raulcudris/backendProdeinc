package com.system.crosscutting.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidad JPA que representa la tabla ORSPLAMDPLANTRABSEMANA.
 *
 * Esta tabla almacena la distribución semanal del plan de trabajo
 * para cada actividad o equipo proyectado.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSPLAMDPLANTRABSEMANA")
public class EntyOrsplamdplantrabsemana implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código secuencial autoincremental generado por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_PLSE")
    private Integer orsPrimarykeyPlse;

    /**
     * Código único funcional del plan semanal.
     */
    @Column(name = "ORS_IDENTIFKEY_PLSE", length = 30, nullable = false)
    private String orsIdentifkeyPlse;

    /**
     * Código único de la orden de servicio.
     */
    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 30)
    private String orsIdentifkeyOrde;

    /**
     * Código único del plan de trabajo principal.
     */
    @Column(name = "ORS_IDENTIFKEY_PLTR", length = 30)
    private String orsIdentifkeyPltr;

    /**
     * Código único de la proyección semanal.
     */
    @Column(name = "ORS_IDENTIFKEY_PSEM", length = 30)
    private String orsIdentifkeyPsem;

    /**
     * Cantidad de unidades programadas en la semana.
     */
    @Column(name = "ORS_CANTIDUNIDAD_PLSE")
    private Integer orsCantidunidadPlse;

    /**
     * Valor unitario programado.
     */
    @Column(name = "ORS_VALORUNIDAD_PLSE", precision = 17, scale = 2)
    private BigDecimal orsValorunidadPlse;

    /**
     * Valor total programado para la semana.
     */
    @Column(name = "ORS_VALORTOTAL_PLSE", precision = 17, scale = 2)
    private BigDecimal orsValortotalPlse;

    /**
     * Cantidad de unidades ejecutadas en la semana.
     */
    @Column(name = "ORS_EJECUTUNIDAD_PLSE")
    private Integer orsEjecutunidadPlse;

    /**
     * Valor total ejecutado en la semana.
     */
    @Column(name = "ORS_VALOREJECUT_PLSE", precision = 17, scale = 2)
    private BigDecimal orsValorejecutPlse;

    /**
     * Tipo de registro: 1=Registro original, 2=Novedad.
     */
    @Column(name = "ORS_TIPOREGIST_PLSE", length = 2)
    private String orsTiporegistPlse;

    /**
     * Estado del registro: 1=Abierto, 2=Cerrado, 3=Cancelado.
     */
    @Column(name = "ORS_ESTADOREG_PLSE", length = 1)
    private String orsEstadoregPlse;
}