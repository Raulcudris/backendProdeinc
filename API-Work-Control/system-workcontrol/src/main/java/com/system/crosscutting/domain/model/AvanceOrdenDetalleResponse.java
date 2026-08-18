package com.system.crosscutting.domain.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class AvanceOrdenDetalleResponse {

    private String rspValue;
    private String rspMessage;
    private String rspParentKey;
    private String rspAppKey;
    private List<AvanceOrdenDetalleDto> rspData = new ArrayList<>();
}