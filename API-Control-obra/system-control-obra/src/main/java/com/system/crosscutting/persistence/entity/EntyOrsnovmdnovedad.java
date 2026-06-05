package com.system.crosscutting.persistence.entity;

import java.io.Serializable;
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
 * Entidad JPA que representa la tabla ORSNOVMDNOVEDAD.
 *
 * Esta tabla almacena las novedades registradas en campo
 * asociadas a un reporte diario de obra.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSNOVMDNOVEDAD")
public class EntyOrsnovmdnovedad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_NOVE")
    private Integer orsPrimarykeyNove;

    @Column(name = "ORS_IDENTIFKEY_NOVE", length = 30, nullable = false)
    private String orsIdentifkeyNove;

    @Column(name = "ORS_IDENTIFKEY_REDI", length = 30, nullable = false)
    private String orsIdentifkeyRedi;

    @Column(name = "ORS_TIPONOVEDAD_NOVE", length = 50)
    private String orsTiponovedadNove;

    @Column(name = "ORS_DESCRIPCION_NOVE", columnDefinition = "TEXT")
    private String orsDescripcionNove;

    @Column(name = "ORS_FECHANOVEDAD_NOVE")
    private LocalDate orsFechanovedadNove;

    @Column(name = "ORS_CRITICIDAD_NOVE", length = 20)
    private String orsCriticidadNove;

    @Column(name = "ORS_RESPONSABLE_NOVE", length = 120)
    private String orsResponsableNove;

    @Column(name = "ORS_REQUIEREACCION_NOVE", length = 1)
    private String orsRequiereaccionNove;

    @Column(name = "ORS_ACCIONTOMADA_NOVE", columnDefinition = "TEXT")
    private String orsAcciontomadaNove;

    @Column(name = "ORS_ESTADOREG_NOVE", length = 1)
    private String orsEstadoregNove;
}