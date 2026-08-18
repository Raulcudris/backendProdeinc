package com.system.crosscutting.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evievimaevidencia")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntyEvievimaevidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evi_primarykey_evid")
    private Integer eviPrimarykeyEvid;

    @Column(name = "evi_identifkey_evid", nullable = false, length = 30)
    private String eviIdentifkeyEvid;

    @Column(name = "evi_identifkey_tiev", nullable = false, length = 30)
    private String eviIdentifkeyTiev;

    @Column(name = "evi_nombrearchivo_evid", length = 200)
    private String eviNombrearchivoEvid;

    @Column(name = "evi_descripcion_evid", length = 500)
    private String eviDescripcionEvid;

    @Column(name = "evi_urlarchivo_evid", nullable = false, length = 700)
    private String eviUrlarchivoEvid;

    @Column(name = "evi_fechacaptura_evid")
    private LocalDate eviFechacapturaEvid;

    @Column(name = "evi_latitud_evid", precision = 12, scale = 8)
    private BigDecimal eviLatitudEvid;

    @Column(name = "evi_longitud_evid", precision = 12, scale = 8)
    private BigDecimal eviLongitudEvid;

    @Column(name = "evi_tiporegist_evid", length = 2)
    private String eviTiporegistEvid;

    @Column(name = "evi_estadoreg_evid", length = 2)
    private String eviEstadoregEvid;
}