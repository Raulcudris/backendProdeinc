package com.system.crosscutting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntyEvitipmatipoevidenciaDto {

    private Integer eviPrimarykeyTiev;

    private String eviIdentifkeyTiev;

    private String eviDescripcionTiev;

    private String eviTiporegistTiev;

    private String eviEstadoregTiev;
}