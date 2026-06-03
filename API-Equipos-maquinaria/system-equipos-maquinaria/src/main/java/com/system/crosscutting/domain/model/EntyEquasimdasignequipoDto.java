package com.system.crosscutting.domain.model;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla EQUASIMDASIGNEQUIPO.
 *
 * Se mantienen los mismos nombres de atributos de la entidad para permitir
 * conversión automática mediante GsonUtil y el patrón Translator.
 */
@Getter
@Setter
public class EntyEquasimdasignequipoDto {

    /**
     * Código secuencial autoincremental de asignación de equipo.
     */
    private Integer equPrimarykeyAseq;

    /**
     * Código único funcional de asignación de equipo.
     */
    private String equIdentifkeyAseq;

    /**
     * Código único funcional del equipo asignado.
     */
    private String equIdentifkeyEqui;

    /**
     * Código único funcional de la orden de servicio.
     */
    private String orsIdentifkeyOrde;

    /**
     * Código único funcional del plan de trabajo.
     */
    private String orsIdentifkeyPltr;

    /**
     * Responsable u operador asignado al equipo.
     */
    private String equResponsableAseq;

    /**
     * Fecha de asignación del equipo.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd")
    private LocalDate equFechaasigAseq;

    /**
     * Fecha de devolución del equipo.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd")
    private LocalDate equFechadevolAseq;

    /**
     * Observaciones de la asignación.
     */
    private String equObservacionAseq;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo, 3=Cerrado.
     */
    private String equEstadoregAseq;
}