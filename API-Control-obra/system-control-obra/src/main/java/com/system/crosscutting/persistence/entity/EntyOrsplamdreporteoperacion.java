package com.system.crosscutting.persistence.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orsplamdreporteoperacion")
public class EntyOrsplamdreporteoperacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ors_primarykey_rope")
    private Integer orsPrimarykeyRope;

    @Column(name = "ors_identifkey_rope", nullable = false, unique = true, length = 30)
    private String orsIdentifkeyRope;

    @Column(name = "ors_identifkey_orde", length = 30)
    private String orsIdentifkeyOrde;

    @Column(name = "ors_identifkey_psem", length = 30)
    private String orsIdentifkeyPsem;

    @Column(name = "ors_identifkey_plse", length = 30)
    private String orsIdentifkeyPlse;

    @Column(name = "ors_identifkey_punt", length = 30)
    private String orsIdentifkeyPunt;

    @Column(name = "ors_fechareport_rope")
    private LocalDate orsFechareportRope;

    @Column(name = "ors_departamento_rope", length = 80)
    private String orsDepartamentoRope;

    @Column(name = "ors_municipio_rope", length = 80)
    private String orsMunicipioRope;

    @Column(name = "ors_sitio_rope", length = 150)
    private String orsSitioRope;

    @Column(name = "prv_identifkey_mprv", length = 20)
    private String prvIdentifkeyMprv;

    @Column(name = "ors_semana_rope")
    private Integer orsSemanaRope;

    @Column(name = "ors_semfechini_rope")
    private LocalDate orsSemfechiniRope;

    @Column(name = "ors_semfechfin_rope")
    private LocalDate orsSemfechfinRope;

    @Column(name = "ors_descsuministro_rope", columnDefinition = "TEXT")
    private String orsDescsuministroRope;

    @Column(name = "ors_actividadhidraulica_rope", length = 1)
    private String orsActividadhidraulicaRope;

    @Column(name = "ors_actividadjarillones_rope", length = 1)
    private String orsActividadjarillonesRope;

    @Column(name = "ors_actividadtaludes_rope", length = 1)
    private String orsActividadtaludesRope;

    @Column(name = "ors_actividadvias_rope", length = 1)
    private String orsActividadviasRope;

    @Column(name = "ors_actividadotro_rope", length = 150)
    private String orsActividadotroRope;

    @Column(name = "ors_observacion_rope", columnDefinition = "TEXT")
    private String orsObservacionRope;

    @Column(name = "ors_firmasuministro_rope", length = 1)
    private String orsFirmasuministroRope;

    @Column(name = "ors_firmaseguimiento_rope", length = 1)
    private String orsFirmaseguimientoRope;

    @Column(name = "ors_fechasistema_rope")
    private LocalDateTime orsFechasistemaRope;

    @Column(name = "ors_tiporegist_rope", length = 2)
    private String orsTiporegistRope;

    @Column(name = "ors_estadoreg_rope", length = 1)
    private String orsEstadoregRope;
}