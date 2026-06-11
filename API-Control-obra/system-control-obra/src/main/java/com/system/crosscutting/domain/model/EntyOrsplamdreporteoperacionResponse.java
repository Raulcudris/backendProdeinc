package com.system.crosscutting.domain.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsplamdreporteoperacionResponse {

    private String rspMessage;

    private String rspValue;

    private String rspParentKey;

    private String rspAppKey;

    private PaginationResponse rspPagination;

    private List<EntyOrsplamdreporteoperacionDto> rspData;
}