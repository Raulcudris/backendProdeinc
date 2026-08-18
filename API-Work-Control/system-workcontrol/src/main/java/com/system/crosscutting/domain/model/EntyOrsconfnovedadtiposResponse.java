package com.system.crosscutting.domain.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsconfnovedadtiposResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String rspValue = "OK";

    private String rspMessage = "OK";

    private String rspParentKey = "NA";

    private String rspAppKey = "WORK-CONTROL";

    private PaginationResponse rspPagination;

    private List<EntyOrsconfnovedadtiposDto> rspData =
            new ArrayList<>();
}