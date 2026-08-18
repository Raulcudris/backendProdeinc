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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ors_primarykey_punt")
    private Integer orsPrimarykeyPunt;

    @Column(name = "ors_identifkey_punt", length = 30, nullable = false)
    private String orsIdentifkeyPunt;

    @Column(name = "ors_identifkey_orde", length = 30)
    private String orsIdentifkeyOrde;

    @Column(name = "ors_nombresitio_punt", length = 150)
    private String orsNombresitioPunt;

    @Column(name = "sis_codpro_sipr", length = 14)
    private String sisCodproSipr;

    @Column(name = "ors_geolatitude_punt")
    private Double orsGeolatitudePunt;

    @Column(name = "ors_geolongitude_punt")
    private Double orsGeolongitudePunt;

    @Column(name = "ors_pathimagen_punt", columnDefinition = "TEXT")
    private String orsPathimagenPunt;

    @Column(name = "ors_tiporegist_punt", length = 2)
    private String orsTiporegistPunt;

    @Column(name = "ors_estadoreg_punt", length = 1)
    private String orsEstadoregPunt;
}