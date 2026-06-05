package com.system.crosscutting.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla ORSNOVMDNOVEDAD.
 */
@Getter
@Setter
public class EntyOrsnovmdnovedadDto {

    private Integer orsPrimarykeyNove;

    private String orsIdentifkeyNove;

    private String orsIdentifkeyRedi;

    private String orsTiponovedadNove;

    private String orsDescripcionNove;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsFechanovedadNove;

    private String orsCriticidadNove;

    private String orsResponsableNove;

    private String orsRequiereaccionNove;

    private String orsAcciontomadaNove;

    private String orsEstadoregNove;
}