package com.system.crosscutting.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyEvievimaevidenciaDto {

    private Integer eviPrimarykeyEvid;
    private String eviIdentifkeyEvid;
    private String eviIdentifkeyTiev;
    private String eviNombrearchivoEvid;
    private String eviDescripcionEvid;
    private String eviUrlarchivoEvid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate eviFechacapturaEvid;

    private BigDecimal eviLatitudEvid;
    private BigDecimal eviLongitudEvid;

    private String eviTiporegistEvid;
    private String eviEstadoregEvid;
}