package com.system.crosscutting.domain.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsordmdsitiospuntosResponse {


    private String rspValue = "OK";

    private String rspMessage = "OK";

    private String rspParentKey = "NA";

    private String rspAppKey = "WORK-CONTROL";

    private PaginationResponse rspPagination;

    private List<EntyOrsordmdsitiospuntosDto> rspData = new ArrayList<>();
}