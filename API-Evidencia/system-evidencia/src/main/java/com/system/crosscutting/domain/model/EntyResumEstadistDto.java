package com.system.crosscutting.domain.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntyResumEstadistDto {
    /** Total contratos realizados */
    @Builder.Default
    private Integer recContractTotal = 0;
    /** Contratos finalizados conformes (la contraparte finalizo conforme) */
    @Builder.Default
    private Integer recContractOkey = 0;
    /** Contratos finalizados no conformes (la contraparte finalizó no conforme) */
    @Builder.Default
    private Integer recContractDown = 0;
    /** sin uso por ahora */
    @Builder.Default
    private Integer recFavorites = 0;
    /** total comentarios realizados al perfil */
    @Builder.Default
    private Integer recCommentsTotal = 0;
    /** calificacion generada por la IA  */
    @Builder.Default
    private Float recQualification = (float) 1.0;
    /** texto generado IA para el perfil */
    @Builder.Default
    private String recConcept = "No hay contratos realizados";
    /** fecha o tiempo en que fue actualiza la estadistica */
    @Builder.Default
    private String recDateConcept = "No se ha actualizado todavia";
}
