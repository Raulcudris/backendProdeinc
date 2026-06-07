package com.system.crosscutting.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO para la tabla prvinvmainventarioequipos.
 */
@Getter
@Setter
public class EntyPrvinvmainventarioequiposDto {

    private Integer prvPrimarykeyInve;

    private String prvIdentifkeyInve;

    private String prvIdentifkeyMprv;

    private String prvTipoequipoTieq;

    private String prvNombrequipoInve;

    private String prvRefermodeloInve;

    private String prvEquipoestadoInve;

    private String prvEquipoactivoInve;

    private String prvEstadoregInve;
}