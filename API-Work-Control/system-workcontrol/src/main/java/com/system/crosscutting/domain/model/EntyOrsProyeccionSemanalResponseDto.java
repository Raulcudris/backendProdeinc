package com.system.crosscutting.domain.model;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsProyeccionSemanalResponseDto {

    private String orsIdentifkeyOrde;
    /**
     * Fecha inicio de la orden.
     * Formato de salida: dd-MM-yyyy
     */
    private String orsPlanfechiniOrde;

    /**
     * Fecha fin de la orden.
     * Formato de salida: dd-MM-yyyy
     */
    private String orsPlanfechfinOrde;

    /**
     * Fechas excluidas válidas, dentro del rango de la orden.
     * Formato: yyMMdd
     */
    private String orsDiasnhabilesOrde;
    private List<EntyOrsProyeccionSemanalDetalleDto> verDetails = new ArrayList<>();
}
