package com.system.crosscutting.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EvidenciaUploadResponse {

    private String rspValue;
    private String rspMessage;
    private String fileName;
    private String originalFileName;
    private String contentType;
    private Long size;
    private String url;
}