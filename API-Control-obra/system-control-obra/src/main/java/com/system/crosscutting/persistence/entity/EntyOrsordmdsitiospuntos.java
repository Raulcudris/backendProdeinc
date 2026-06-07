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
 * Entidad JPA que representa la tabla ORSORDMDSITIOSPUNTOS.
 *
 * Esta tabla almacena los sitios geográficos o puntos de trabajo
 * asociados a una orden de servicio.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSORDMDSITIOSPUNTOS")
public class EntyOrsordmdsitiospuntos implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código secuencial autoincremental generado por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_PUNT")
    private Integer orsPrimarykeyPunt;

    /**
     * Código único funcional del sitio o punto.
     */
    @Column(name = "ORS_IDENTIFKEY_PUNT", length = 30, nullable = false)
    private String orsIdentifkeyPunt;

    /**
     * Código único de la orden de servicio.
     */
    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 30)
    private String orsIdentifkeyOrde;

    /**
     * Nombre del sitio o punto a intervenir.
     */
    @Column(name = "ORS_NOMBRESITIO_PUNT", length = 150)
    private String orsNombresitioPunt;

    /**
     * Código del lugar, municipio, provincia o ciudad.
     */
    @Column(name = "SIS_CODPRO_SIPR", length = 14)
    private String sisCodproSipr;

    /**
     * Latitud del sitio.
     */
    @Column(name = "ORS_GEOLATITUDE_PUNT", precision = 14, scale = 10)
    private BigDecimal orsGeolatitudePunt;

    /**
     * Longitud del sitio.
     */
    @Column(name = "ORS_GEOLONGITUDE_PUNT", precision = 14, scale = 10)
    private BigDecimal orsGeolongitudePunt;

    /**
     * Ruta de imagen asociada al sitio.
     */
    @Column(name = "ORS_PATHIMAGEN_PUNT", columnDefinition = "TEXT")
    private String orsPathimagenPunt;

    /**
     * Tipo de registro: 1=Registro original, 2=Novedad.
     */
    @Column(name = "ORS_TIPOREGIST_PUNT", length = 2)
    private String orsTiporegistPunt;

    /**
     * Estado del registro: 1=Abierto, 2=Cerrado, 3=Cancelado.
     */
    @Column(name = "ORS_ESTADOREG_PUNT", length = 1)
    private String orsEstadoregPunt;
}