package com.system.crosscutting.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntyEvievimaevidenciaResponse {

    private String rspValue;

    private String rspMessage;

    private String rspParentKey;

    private String rspAppKey;

    private PaginationResponse rspPagination;

    private List<EntyEvievimaevidenciaDto> rspData;
}