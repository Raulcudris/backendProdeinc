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
 * Entidad JPA que representa la tabla ORSORDMDRESUMENEQUIPOS.
 *
 * Esta tabla almacena el resumen de equipos autorizados
 * dentro de una orden de servicio.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSORDMDRESUMENEQUIPOS")
public class EntyOrsordmdresumenequipos implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código secuencial autoincremental generado por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_RSEQ")
    private Integer orsPrimarykeyRseq;

    /**
     * Código único funcional del resumen de equipo.
     */
    @Column(name = "ORS_IDENTIFKEY_RSEQ", length = 30, nullable = false)
    private String orsIdentifkeyRseq;

    /**
     * Código único de la orden de servicio.
     */
    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 30)
    private String orsIdentifkeyOrde;

    /**
     * Código del tipo de equipo o maquinaria.
     */
    @Column(name = "PRV_TIPOEQUIPO_TIEQ", length = 5)
    private String prvTipoequipoTieq;

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
    @Column(name = "ORS_TIPOREGIST_RSEQ", length = 2)
    private String orsTiporegistRseq;

    /**
     * Estado del registro: 1=Abierto, 2=Cerrado, 3=Cancelado.
     */
    @Column(name = "ORS_ESTADOREG_RSEQ", length = 1)
    private String orsEstadoregRseq;
}