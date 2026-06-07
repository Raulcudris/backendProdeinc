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
 * Entidad JPA que representa la tabla ORSPLAMAPLANDETRABAJO.
 *
 * Esta tabla almacena el plan de trabajo proyectado
 * para una orden de servicio y un sitio específico.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSPLAMAPLANDETRABAJO")
public class EntyOrsplamaplandetrabajo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código secuencial autoincremental generado por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_PLTR")
    private Integer orsPrimarykeyPltr;

    /**
     * Código único funcional del plan de trabajo.
     */
    @Column(name = "ORS_IDENTIFKEY_PLTR", length = 30, nullable = false)
    private String orsIdentifkeyPltr;

    /**
     * Código único de la orden de servicio.
     */
    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 30)
    private String orsIdentifkeyOrde;

    /**
     * Código único del sitio o punto a intervenir.
     */
    @Column(name = "ORS_IDENTIFKEY_PUNT", length = 30)
    private String orsIdentifkeyPunt;

    /**
     * Descripción de la actividad a realizar.
     */
    @Column(name = "ORS_DESACTIVIDAD_PLTR", length = 250)
    private String orsDesactividadPltr;

    /**
     * Código único del resumen de equipos autorizado.
     */
    @Column(name = "ORS_IDENTIFKEY_RSEQ", length = 30)
    private String orsIdentifkeyRseq;

    /**
     * Código del inventario de equipo asignado.
     */
    @Column(name = "PRV_IDENTIFKEY_INVE", length = 10)
    private String prvIdentifkeyInve;

    /**
     * Cantidad de unidades autorizadas.
     */
    @Column(name = "ORS_CANTIDUNIDAD_RSEQ")
    private Integer orsCantidunidadRseq;

    /**
     * Valor unitario autorizado.
     */
    @Column(name = "ORS_VALORUNIDAD_RSEQ", precision = 17, scale = 2)
    private BigDecimal orsValorunidadRseq;

    /**
     * Valor total autorizado.
     */
    @Column(name = "ORS_VALORTOTAL_RSEQ", precision = 17, scale = 2)
    private BigDecimal orsValortotalRseq;

    /**
     * Tipo de registro: 1=Registro original, 2=Novedad.
     */
    @Column(name = "ORS_TIPOREGIST_PLTR", length = 2)
    private String orsTiporegistPltr;

    /**
     * Estado del registro: 1=Abierto, 2=Cerrado, 3=Cancelado.
     */
    @Column(name = "ORS_ESTADOREG_PLTR", length = 1)
    private String orsEstadoregPltr;
}