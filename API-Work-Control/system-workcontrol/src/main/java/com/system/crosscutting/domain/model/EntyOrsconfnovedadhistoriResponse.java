package com.system.crosscutting.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsconfnovedadhistoriResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String rspValue = "OK";

    private String rspMessage = "OK";

    private String rspParentKey = "NA";

    private String rspAppKey = "WORK-CONTROL";

    private PaginationResponse rspPagination;

    private List<EntyOrsconfnovedadhistoriDto> rspData =
            new ArrayList<>();
}