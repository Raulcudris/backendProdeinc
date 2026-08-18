package com.system.crosscutting.domain.model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntyOrsordmaordenservicioResponse implements Serializable {


    @Builder.Default
    private String rspValue = "OK";

    @Builder.Default
    private String rspMessage = "OK";

    @Builder.Default
    private String rspParentKey = "NA";

    @Builder.Default
    private String rspAppKey = "WORK-CONTROL";

    @Builder.Default
    private PaginationResponse rspPagination = new PaginationResponse();

    @Builder.Default
    private List<EntyOrsordmaordenservicioDto> rspData = new ArrayList<>();
}