package com.system.crosscutting.persistence.entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orsplamddetalleequipooperacion")
public class EntyOrsplamddetalleequipooperacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ors_primarykey_deop")
    private Integer orsPrimarykeyDeop;

    @Column(name = "ors_identifkey_deop", nullable = false, unique = true, length = 30)
    private String orsIdentifkeyDeop;

    @Column(name = "ors_identifkey_rope", length = 30)
    private String orsIdentifkeyRope;

    @Column(name = "ors_identifkey_orde", length = 30)
    private String orsIdentifkeyOrde;

    @Column(name = "ors_identifkey_psem", length = 30)
    private String orsIdentifkeyPsem;

    @Column(name = "ors_identifkey_plse", length = 30)
    private String orsIdentifkeyPlse;

    @Column(name = "ors_identifkey_punt", length = 30)
    private String orsIdentifkeyPunt;

    @Column(name = "prv_identifkey_inve", length = 10)
    private String prvIdentifkeyInve;

    @Column(name = "prv_tipoequipo_tieq", length = 5)
    private String prvTipoequipoTieq;

    @Column(name = "ors_nombrequipo_deop", length = 100)
    private String orsNombrequipoDeop;

    @Column(name = "ors_refermodelo_deop", length = 80)
    private String orsRefermodeloDeop;

    @Column(name = "ors_nroregistro_deop", length = 30)
    private String orsNroregistroDeop;

    @Column(name = "ors_unidad_deop", length = 5)
    private String orsUnidadDeop;

    @Column(name = "ors_tipocontrol_deop", length = 20)
    private String orsTipocontrolDeop;

    @Column(name = "ors_horometroini_deop", precision = 12, scale = 2)
    private BigDecimal orsHorometroiniDeop;

    @Column(name = "ors_horometrofin_deop", precision = 12, scale = 2)
    private BigDecimal orsHorometrofinDeop;

    @Column(name = "ors_horastrabajadas_deop", precision = 12, scale = 2)
    private BigDecimal orsHorastrabajadasDeop;

    @Column(name = "ors_kminicial_deop", precision = 12, scale = 2)
    private BigDecimal orsKminicialDeop;

    @Column(name = "ors_kmfinal_deop", precision = 12, scale = 2)
    private BigDecimal orsKmfinalDeop;

    @Column(name = "ors_kmrecorrido_deop", precision = 12, scale = 2)
    private BigDecimal orsKmrecorridoDeop;

    @Column(name = "ors_diatrabajado_deop", precision = 12, scale = 2)
    private BigDecimal orsDiatrabajadoDeop;

    @Column(name = "ors_valorunidad_deop", precision = 17, scale = 2)
    private BigDecimal orsValorunidadDeop;

    @Column(name = "ors_valorejecutado_deop", precision = 17, scale = 2)
    private BigDecimal orsValorejecutadoDeop;

    @Column(name = "ors_fechatrabajo_deop")
    private LocalDate orsFechatrabajoDeop;

    @Column(name = "ors_observacion_deop", columnDefinition = "TEXT")
    private String orsObservacionDeop;

    @Column(name = "ors_firmasuministro_deop", length = 1)
    private String orsFirmasuministroDeop;

    @Column(name = "ors_firmaseguimiento_deop", length = 1)
    private String orsFirmaseguimientoDeop;

    @Column(name = "ors_fechasistema_deop")
    private LocalDateTime orsFechasistemaDeop;

    @Column(name = "ors_tiporegist_deop", length = 2)
    private String orsTiporegistDeop;

    @Column(name = "ors_estadoreg_deop", length = 1)
    private String orsEstadoregDeop;
}