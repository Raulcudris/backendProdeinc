package com.system.crosscutting.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla ORSSITMDSITIO.
 */
@Getter
@Setter
public class EntyOrssitmdsitioDto {

    private Integer orsPrimarykeySitr;

    private String orsIdentifkeySitr;

    private String orsIdentifkeyOrde;

    private String orsNombreSitr;

    private String orsDescripcionSitr;

    private String orsUbicacionSitr;

    private Float orsLatitudSitr;

    private Float orsLongitudSitr;

    private String orsEstadoregSitr;
}