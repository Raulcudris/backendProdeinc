package com.system.crosscutting.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyPrvmaeproveedoresmaDto {

    private Integer prvPrimarykeyMprv;
    private String prvIdentifkeyMprv;
    private String prvNumeronitMprv;
    private String prvRazonsocialMprv;
    private String prvObjetosocialMprv;
    private String sisTiposociedadTpso;
    private String sisCodactividadCiiu;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate prvFechconstMprv;

    private String prvPaginawebMprv;
    private String prvDireccionMprv;
    private String prvTelefonoMprv;
    private String prvCorreoMprv;
    private String sisCodpaiSipa;
    private String sisIdedptSidp;
    private String sisCodproSipr;
    private String prvCodposMprv;
    private String prvIdentifkeyRelg;
    private String prvEstadoregMprv;
}