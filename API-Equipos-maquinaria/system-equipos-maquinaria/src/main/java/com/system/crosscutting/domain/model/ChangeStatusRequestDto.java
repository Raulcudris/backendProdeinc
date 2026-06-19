package com.system.crosscutting.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeStatusRequestDto {

    private Integer recPKey;
    private String recEstreg;
}