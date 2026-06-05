package com.system.crosscutting.domain.model;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla PRVMAEPROVEEDORESMA.
 */
@Getter
@Setter
public class EntyPrvmaeproveedoresmaDto {

    private Integer prvPrimarykeyMprv;

    private String prvIdentifkeyMprv;

    private String prvNumeronitMprv;

    private String prvRazonsocialMprv;

    private String prvNombrecomercialMprv;

    private String prvTipoproveedorMprv;

    private String prvContactoMprv;

    private String prvTelefonoMprv;

    private String prvCorreoMprv;

    private String prvDireccionMprv;

    private String prvCiudadMprv;

    private String prvDepartamentoMprv;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate prvFecharegistroMprv;

    private String prvObservacionMprv;

    private String prvEstadoregMprv;
}