package com.system.crosscutting.domain.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntyCityDto
{
    //EntySispaisciudadmd
    private String  sisCodproSipr;
    private String  sisNombreSipr;
    /** 1=Cabecera Municipal 2=Provincia-Corregimiento 3=Veredas-caserios 4=Rural disperso */
    private String  sisProclaSipr; 
    // EntySispaisciudadmd: Ciudad Cabecera municipal o capital
    private String sisCodmunSimu;
    private String sisNombreSimu;
    //EntySispaisbestados
    private String  sisIdedptSidp;
    private String  sisCoddptSidp; 
    private String  sisNombreSidp;
    //EntySispaisamaestro
    private Integer  recUnikeySipa;
    private String   sisCodpaiSipa;
    private String   sisNombreSipa;
}
