package com.system.crosscutting.domain.model;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsProyeccionSemanalApiResponse {
    private String rspValue = "OK";

    private String rspMessage = "OK";

    private String rspParentKey = "NA";

    private String rspAppKey = "NA";

    private List<EntyOrsProyeccionSemanalResponseDto> rspData =  new ArrayList<>();
}
