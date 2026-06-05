package com.system.crosscutting.persistence.entity;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidad JPA que representa la tabla PRVMAEPROVEEDORESMA.
 *
 * Esta tabla almacena proveedores de maquinaria, materiales,
 * servicios y suministros asociados a obras civiles.
 */
@Getter
@Setter
@Entity
@Table(name = "prvmaeproveedoresma")
public class EntyPrvmaeproveedoresma implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRV_PRIMARYKEY_MPRV")
    private Integer prvPrimarykeyMprv;

    @Column(name = "PRV_IDENTIFKEY_MPRV", length = 20, nullable = false)
    private String prvIdentifkeyMprv;

    @Column(name = "PRV_NIT_MPRV", length = 30)
    private String prvNitMprv;

    @Column(name = "PRV_RAZONSOCIAL_MPRV", length = 200)
    private String prvRazonsocialMprv;

    @Column(name = "PRV_NOMBRECOMERCIAL_MPRV", length = 200)
    private String prvNombrecomercialMprv;

    @Column(name = "PRV_TIPOPROVEEDOR_MPRV", length = 50)
    private String prvTipoproveedorMprv;

    @Column(name = "PRV_CONTACTO_MPRV", length = 120)
    private String prvContactoMprv;

    @Column(name = "PRV_TELEFONO_MPRV", length = 50)
    private String prvTelefonoMprv;

    @Column(name = "PRV_CORREO_MPRV", length = 120)
    private String prvCorreoMprv;

    @Column(name = "PRV_DIRECCION_MPRV", length = 200)
    private String prvDireccionMprv;

    @Column(name = "PRV_CIUDAD_MPRV", length = 100)
    private String prvCiudadMprv;

    @Column(name = "PRV_DEPARTAMENTO_MPRV", length = 100)
    private String prvDepartamentoMprv;

    @Column(name = "PRV_FECHAREGISTRO_MPRV")
    private LocalDate prvFecharegistroMprv;

    @Column(name = "PRV_OBSERVACION_MPRV", columnDefinition = "TEXT")
    private String prvObservacionMprv;

    @Column(name = "PRV_ESTADOREG_MPRV", length = 1)
    private String prvEstadoregMprv;
}