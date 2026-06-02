package com.system.crosscutting.domain.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EntyRechomeestadistDto {
    private Integer recIdentifkeyRhes;
    private String  recTyperegisRhes;
    private String  recIdenumkeyRhes;
    private String  recKeylocateRhes;
    private Integer recRegcountRhes;
    private String  recStatusregiRhes;
}
