package com.system.crosscutting.domain.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AvanceOrdenResponse {

    private String rspValue;
    private String rspMessage;
    private String rspParentKey;
    private String rspAppKey;
    private List<AvanceOrdenDto> rspData = new ArrayList<>();
}