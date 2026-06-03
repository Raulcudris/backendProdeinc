package com.system.crosscutting.persistence.entity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad JPA que representa la tabla EVIREFMDREFERENCIA.
 *
 * Esta tabla almacena las referencias de evidencias hacia otras entidades del sistema,
 * permitiendo asociar fotos, videos, documentos, audios o archivos a órdenes de servicio,
 * planes de trabajo, reportes diarios, novedades, documentos o equipos.
 */
@Getter
@Setter
@Entity
@Table(name = "EVIREFMDREFERENCIA")
public class EntyEvirefmdreferencia {

    /**
     * Código secuencial autoincremental de la referencia de evidencia.
     */
    @Id
    @Column(name = "EVI_PRIMARYKEY_EVRE")
    private Integer eviPrimarykeyEvre;

    /**
     * Código único funcional de referencia de evidencia.
     */
    @Column(name = "EVI_IDENTIFKEY_EVRE", length = 30, nullable = false)
    private String eviIdentifkeyEvre;

    /**
     * Código único funcional de la evidencia.
     */
    @Column(name = "EVI_IDENTIFKEY_EVID", length = 30, nullable = false)
    private String eviIdentifkeyEvid;

    /**
     * Tipo de referencia: ORDEN_SERVICIO, PLAN_TRABAJO, REPORTE_DIARIO, NOVEDAD, DOCUMENTO, EQUIPO.
     */
    @Column(name = "EVI_TIPOREFEREN_EVRE", length = 40, nullable = false)
    private String eviTiporeferenEvre;

    /**
     * Código identificador del registro referenciado.
     */
    @Column(name = "EVI_REFERENCIAID_EVRE", length = 30, nullable = false)
    private String eviReferenciaidEvre;

    /**
     * Observación de la referencia.
     */
    @Column(name = "EVI_OBSERVACION_EVRE", columnDefinition = "TEXT")
    private String eviObservacionEvre;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    @Column(name = "EVI_ESTADOREG_EVRE", length = 1, nullable = false)
    private String eviEstadoregEvre;
}
