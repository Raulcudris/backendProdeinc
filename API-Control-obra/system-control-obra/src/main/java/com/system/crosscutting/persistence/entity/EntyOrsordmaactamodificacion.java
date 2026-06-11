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
@Table(name = "orsordmaactamodificacion")
public class EntyOrsordmaactamodificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ors_primarykey_acmo")
    private Integer orsPrimarykeyAcmo;

    @Column(name = "ors_identifkey_acmo", nullable = false, unique = true, length = 30)
    private String orsIdentifkeyAcmo;

    @Column(name = "ors_identifkey_orde", length = 30)
    private String orsIdentifkeyOrde;

    @Column(name = "ors_numeroacta_acmo", length = 30)
    private String orsNumeroactaAcmo;

    @Column(name = "ors_fechaacta_acmo")
    private LocalDate orsFechaactaAcmo;

    @Column(name = "ors_tipomodificacion_acmo", length = 40)
    private String orsTipomodificacionAcmo;

    @Column(name = "ors_causal_acmo", columnDefinition = "TEXT")
    private String orsCausalAcmo;

    @Column(name = "ors_valorinicial_acmo", precision = 17, scale = 2)
    private BigDecimal orsValorinicialAcmo;

    @Column(name = "ors_valormodificacion_acmo", precision = 17, scale = 2)
    private BigDecimal orsValormodificacionAcmo;

    @Column(name = "ors_valoractualizado_acmo", precision = 17, scale = 2)
    private BigDecimal orsValoractualizadoAcmo;

    @Column(name = "ors_saldoaliberar_acmo", precision = 17, scale = 2)
    private BigDecimal orsSaldoaliberarAcmo;

    @Column(name = "ors_estadoacta_acmo", length = 20)
    private String orsEstadoactaAcmo;

    @Column(name = "ors_fechasistema_acmo")
    private LocalDateTime orsFechasistemaAcmo;

    @Column(name = "ors_tiporegist_acmo", length = 2)
    private String orsTiporegistAcmo;

    @Column(name = "ors_estadoreg_acmo", length = 1)
    private String orsEstadoregAcmo;
}