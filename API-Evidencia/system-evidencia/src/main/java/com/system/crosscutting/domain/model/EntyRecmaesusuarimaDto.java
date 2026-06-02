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
public class EntyRecmaesusuarimaDto {
    private Integer recIdeunikeyReus;
    private String  recNroregReus;
    private String  recNiknamReus;
    private String  recNroideReus;
    private String  recNombreReus;
    private String  recApelidReus;
    private String  recFecnacReus;
    private String  recSexusuReus;
    private String  recNomusuReus;
    private String  recImgvisReus;
    private String  recDirresReus;
    private String  recTelefoReus;
    private String  apjCorreoApgm;
    private String  sisCodpaiSipa;
    private String  sisIdedptSidp;
    private String  sisCodproSipr;
    private String  recCodposReus;
    private Float   recGeolatReus;
    private Float   recGeolonReus;
    private Integer sisCountaRkey;
    private Integer sisCountbRkey;
    private Integer sisCountcRkey;
    private Integer sisCountdRkey;
    private Integer sisCounteRkey;
    private Integer sisCountfRkey;
    private String recEstregReus;
    private EntyCityDto city;
    EntyResumEstadistDto resumEstadist;
}
