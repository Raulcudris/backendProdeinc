package com.system.crosscutting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntyEvirefmdreferenciaDto {

    private Integer eviPrimarykeyRefe;

    private String eviIdentifkeyRefe;

    private String eviIdentifkeyEvid;

    private String eviTiporegistroRefe;

    private String eviIdentifregistroRefe;

    private String eviObservacionRefe;

    private String eviTiporegistRefe;

    private String eviEstadoregRefe;
}