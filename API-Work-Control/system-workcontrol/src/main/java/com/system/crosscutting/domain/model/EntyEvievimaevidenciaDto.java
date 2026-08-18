package com.system.crosscutting.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntyEvievimaevidenciaDto {

    private Integer eviPrimarykeyEvid;

    private String eviIdentifkeyEvid;

    private String eviIdentifkeyTiev;

    private String eviNombrearchivoEvid;

    private String eviDescripcionEvid;

    private String eviUrlarchivoEvid;

    private LocalDate eviFechacapturaEvid;

    private BigDecimal eviLatitudEvid;

    private BigDecimal eviLongitudEvid;

    private String eviTiporegistEvid;

    private String eviEstadoregEvid;
}