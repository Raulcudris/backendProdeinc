package com.system.crosscutting.domain.model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsProyeccionSemanalRequestDto {
    private String orsIdentifkeyOrde;

    /**
     * Fecha inicio de labores.
     * Formato esperado: dd-MM-yyyy
     */
    private String orsPlanfechiniOrde;

    /**
     * Fecha fin de labores.
     * Formato esperado: dd-MM-yyyy
     */
    private String orsPlanfechfinOrde;

    /**
     * Fechas excluidas/no hábiles separadas por coma.
     * Formato esperado: dd-MM-yyyy,dd-MM-yyyy
     */
    private String orsDiasnhabilesOrde;
}
