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
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

/**
 * Entidad JPA que representa la tabla ORSORDMAORDENSERVICIO.
 *
 * Esta tabla almacena la información maestra de las órdenes de servicio
 * registradas para la ejecución de obras civiles.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSORDMAORDENSERVICIO")
public class EntyOrsordmaordenservicio implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código secuencial autoincremental generado por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_ORDE")
    private Integer orsPrimarykeyOrde;

    /**
     * Código único funcional de la orden de servicio.
     */
    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 30, nullable = false)
    private String orsIdentifkeyOrde;

    /**
     * Fecha de expedición de la autorización del servicio.
     */
    @Column(name = "ORS_AUTORIFECHA_ORDE")
    private LocalDate orsAutorifechaOrde;

    /**
     * Código del servicio básico autorizado.
     */
    @Column(name = "ORS_CODSERVICIO_SEBS", length = 5)
    private String orsCodservicioSebs;

    /**
     * Evento que originó la prestación del servicio.
     */
    @Column(name = "ORS_SERVICEVENT_ORDE", length = 150)
    private String orsServiceventOrde;

    /**
     * Lugar donde se realizará la prestación del servicio.
     */
    @Column(name = "ORS_SERVICLUGAR_ORDE", length = 150)
    private String orsServiclugarOrde;

    /**
     * Objeto del contrato o descripción amplia del servicio.
     */
    @Column(name = "ORS_SERVICOBJETO_ORDE", columnDefinition = "TEXT")
    private String orsServicobjetoOrde;

    /**
     * Fecha de inicio del plan de trabajo.
     */
    @Column(name = "ORS_PLANFECHINI_ORDE")
    private LocalDate orsPlanfechiniOrde;

    /**
     * Fecha de finalización del plan de trabajo.
     */
    @Column(name = "ORS_PLANFECHFIN_ORDE")
    private LocalDate orsPlanfechfinOrde;

    /**
     * Código único del proveedor autorizado.
     */
    @Column(name = "PRV_IDENTIFKEY_MPRV", length = 20)
    private String prvIdentifkeyMprv;

    /**
     * Código único del representante legal.
     */
    @Column(name = "PRV_IDENTIFKEY_RELG", length = 30)
    private String prvIdentifkeyRelg;

    /**
     * Código del tipo de valor autorizado.
     *
     * Campo no persistido en ORSORDMAORDENSERVICIO porque la columna
     * ORD_TIPOVALOR_TIVA no existe actualmente en la tabla real.
     */
    @Transient
    private String ordTipovalorTiva;

    /**
     * Valor base del servicio autorizado.
     */
    @Column(name = "ORS_VALORBASE_ORDE", precision = 17, scale = 2)
    private BigDecimal orsValorbaseOrde;

    /**
     * Valor del IVA.
     *
     * Mantener como @Column solo si la columna ORS_VALORDEIVA_ORDE existe
     * físicamente en la tabla ORSORDMAORDENSERVICIO.
     */
    @Column(name = "ORS_VALORDEIVA_ORDE", precision = 17, scale = 2)
    private BigDecimal orsValordeivaOrde;

    /**
     * Valor total general.
     */
    @Column(name = "ORS_VALORTOTAL_ORDE", precision = 17, scale = 2)
    private BigDecimal orsValortotalOrde;

    /**
     * Valor total abonado.
     *
     * Campo no persistido en ORSORDMAORDENSERVICIO porque la columna
     * CAR_VALABO_CAMG no existe actualmente en la tabla real.
     */
    @Transient
    private BigDecimal carValaboCamg;

    /**
     * Valor saldo pendiente.
     *
     * Campo no persistido en ORSORDMAORDENSERVICIO porque la columna
     * CAR_VALSAL_CAMG no existe actualmente en la tabla real.
     */
    @Transient
    private BigDecimal carValsalCamg;

    /**
     * Tipo de registro: 1=Registro original, 2=Novedad.
     *
     * Mantener como @Column solo si la columna ORS_TIPOREGIST_ORDE existe
     * físicamente en la tabla ORSORDMAORDENSERVICIO.
     */
    @Column(name = "ORS_TIPOREGIST_ORDE", length = 2)
    private String orsTiporegistOrde;

    /**
     * Estado de la orden: 1=Abierto, 2=Cerrado, 3=Cancelado.
     */
    @Column(name = "ORS_ESTADOREG_ORDE", length = 1)
    private String orsEstadoregOrde;
}