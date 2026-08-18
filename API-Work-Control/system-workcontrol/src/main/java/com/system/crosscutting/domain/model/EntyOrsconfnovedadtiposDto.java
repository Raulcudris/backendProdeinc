package com.system.crosscutting.domain.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsconfnovedadtiposDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer orsPrimarykeyNovt;

    private String orsTiponovedadNovt;

    private String orsDescnovedadNovt;

    private String orsEstadoregNovt;
}