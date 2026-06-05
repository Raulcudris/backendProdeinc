package com.system.crosscutting.domain.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvanceObraResponse {

    private String rspMessage;
    private String rspValue;
    private String rspParentKey;
    private String rspAppKey;
    private List<AvanceObraDto> rspData;
}