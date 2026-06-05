package com.system.crosscutting.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Entidad JPA que representa la tabla EVIEVIMAEVIDENCIA.
 *
 * Esta tabla almacena las evidencias reales cargadas al sistema, como fotografías,
 * videos, audios, documentos o archivos generales, incluyendo URL de almacenamiento,
 * ubicación geográfica, fecha de captura y usuario creador.
 */
@Getter
@Setter
@Entity
@Table(name = "EVIEVIMAEVIDENCIA")
public class EntyEvievimaevidencia {

    /**
     * Código secuencial autoincremental de la evidencia.
     */
    @Id
    @Column(name = "EVI_PRIMARYKEY_EVID")
    private Integer eviPrimarykeyEvid;

    /**
     * Código único funcional de la evidencia.
     */
    @Column(name = "EVI_IDENTIFKEY_EVID", length = 30, nullable = false)
    private String eviIdentifkeyEvid;

    /**
     * Código único funcional del tipo de evidencia.
     */
    @Column(name = "EVI_IDENTIFKEY_TIEV", length = 20, nullable = false)
    private String eviIdentifkeyTiev;

    /**
     * Nombre de la evidencia.
     */
    @Column(name = "EVI_NOMBRE_EVID", length = 150, nullable = false)
    private String eviNombreEvid;

    /**
     * Descripción de la evidencia.
     */
    @Column(name = "EVI_DESCRIPCION_EVID", columnDefinition = "TEXT")
    private String eviDescripcionEvid;

    /**
     * URL o ruta del archivo en almacenamiento externo.
     */
    @Column(name = "EVI_URLARCHIVO_EVID", columnDefinition = "TEXT", nullable = false)
    private String eviUrlarchivoEvid;

    /**
     * Latitud donde fue capturada la evidencia.
     */
    @Column(name = "EVI_LATITUD_EVID")
    private Float eviLatitudEvid;

    /**
     * Longitud donde fue capturada la evidencia.
     */
    @Column(name = "EVI_LONGITUD_EVID")
    private Float eviLongitudEvid;

    /**
     * Fecha y hora de captura de la evidencia.
     */
    @Column(name = "EVI_FECHACAPTURA_EVID")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eviFechacapturaEvid;

    /**
     * Usuario que registró la evidencia.
     */
    @Column(name = "EVI_USUARIOCREA_EVID", length = 80)
    private String eviUsuariocreaEvid;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    @Column(name = "EVI_ESTADOREG_EVID", length = 1, nullable = false)
    private String eviEstadoregEvid;
}