package com.system.crosscutting.domain.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntyRecmaesusuarimaResponse {
    
    @Builder.Default
    private  String rspValue ="OK";
    @Builder.Default
    private  String rspMessage ="OK";
    @Builder.Default
    private  String rspParentKey = "NA";
    @Builder.Default
    private  String rspAppKey = "NA";
    @Builder.Default
    private  PaginationResponse rspPagination = new PaginationResponse();
    private  List<EntyRecmaesusuarimaDto> rspData;
}
