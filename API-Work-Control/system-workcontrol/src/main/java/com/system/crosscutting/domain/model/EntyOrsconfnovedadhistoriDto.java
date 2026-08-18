package com.system.crosscutting.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsconfnovedadhistoriDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer orsPrimarykeyNove;

    private String orsIdentifkeyNove;

    private String orsIdentifkeyOrde;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orsFechreportNove;

    private String orsTiponovedadNovt;

    private String orsRegistrbaseNove;

    private String orsRegistrnoveNove;

    private String orsEstadoregNove;
}