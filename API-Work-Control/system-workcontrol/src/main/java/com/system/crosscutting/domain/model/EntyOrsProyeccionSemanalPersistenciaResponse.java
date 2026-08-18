package com.system.crosscutting.domain.model;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EntyOrsProyeccionSemanalPersistenciaResponse {

    private String rspValue = "OK";

    private String rspMessage = "OK";

    private String rspParentKey = "NA";

    private String rspAppKey = "WORK-CONTROL";

    private PaginationResponse rspPagination;

    private List<EntyOrsProyeccionSemanalResponseDto> rspData =
            new ArrayList<>();
}
