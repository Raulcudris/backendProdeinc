package com.system.crosscutting.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orsordmdactamodificaciondetalle")
public class EntyOrsordmdactamodificaciondetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ors_primarykey_acmd")
    private Integer orsPrimarykeyAcmd;

    @Column(name = "ors_identifkey_acmd", nullable = false, unique = true, length = 30)
    private String orsIdentifkeyAcmd;

    @Column(name = "ors_identifkey_acmo", length = 30)
    private String orsIdentifkeyAcmo;

    @Column(name = "ors_identifkey_orde", length = 30)
    private String orsIdentifkeyOrde;

    @Column(name = "ors_identifkey_rseq", length = 30)
    private String orsIdentifkeyRseq;

    @Column(name = "prv_tipoequipo_tieq", length = 5)
    private String prvTipoequipoTieq;

    @Column(name = "ors_descripcion_equipo_acmd", length = 150)
    private String orsDescripcionEquipoAcmd;

    @Column(name = "ors_unidad_acmd", length = 5)
    private String orsUnidadAcmd;

    @Column(name = "ors_cantidadoriginal_acmd", precision = 17, scale = 2)
    private BigDecimal orsCantidadoriginalAcmd;

    @Column(name = "ors_valorunitario_acmd", precision = 17, scale = 2)
    private BigDecimal orsValorunitarioAcmd;

    @Column(name = "ors_valororiginal_acmd", precision = 17, scale = 2)
    private BigDecimal orsValororiginalAcmd;

    @Column(name = "ors_cantidadanterior_acmd", precision = 17, scale = 2)
    private BigDecimal orsCantidadanteriorAcmd;

    @Column(name = "ors_valoranterior_acmd", precision = 17, scale = 2)
    private BigDecimal orsValoranteriorAcmd;

    @Column(name = "ors_cantidadmodificada_acmd", precision = 17, scale = 2)
    private BigDecimal orsCantidadmodificadaAcmd;

    @Column(name = "ors_valormodificado_acmd", precision = 17, scale = 2)
    private BigDecimal orsValormodificadoAcmd;

    @Column(name = "ors_cantidadactualizada_acmd", precision = 17, scale = 2)
    private BigDecimal orsCantidadactualizadaAcmd;

    @Column(name = "ors_valoractualizado_acmd", precision = 17, scale = 2)
    private BigDecimal orsValoractualizadoAcmd;

    @Column(name = "ors_observacion_acmd", columnDefinition = "TEXT")
    private String orsObservacionAcmd;

    @Column(name = "ors_fechasistema_acmd")
    private LocalDateTime orsFechasistemaAcmd;

    @Column(name = "ors_tiporegist_acmd", length = 2)
    private String orsTiporegistAcmd;

    @Column(name = "ors_estadoreg_acmd", length = 1)
    private String orsEstadoregAcmd;
}