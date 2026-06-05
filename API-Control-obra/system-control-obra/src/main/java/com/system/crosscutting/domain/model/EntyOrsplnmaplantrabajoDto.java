package com.system.crosscutting.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla ORSPLNMAPLANTRABAJO.
 */
@Getter
@Setter
public class EntyOrsplnmaplantrabajoDto {

    private Integer orsPrimarykeyPltr;

    private String orsIdentifkeyPltr;

    private String orsIdentifkeyOrde;

    private String orsIdentifkeySitr;

    private String orsActividadPltr;

    private String orsDescripcionPltr;

    private String orsUnidadmedidaPltr;

    private BigDecimal orsCantidadprogPltr;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsFechainicioPltr;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsFechafinPltr;

    private String orsEstadoregPltr;
}