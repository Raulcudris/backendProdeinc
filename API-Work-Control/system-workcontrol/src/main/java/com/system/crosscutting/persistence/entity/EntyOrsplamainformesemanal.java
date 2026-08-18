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
@Table(name = "orsplamainformesemanal")
public class EntyOrsplamainformesemanal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ors_primarykey_inse")
    private Integer orsPrimarykeyInse;

    @Column(name = "ors_identifkey_inse", nullable = false, unique = true, length = 30)
    private String orsIdentifkeyInse;

    @Column(name = "ors_identifkey_orde", length = 30)
    private String orsIdentifkeyOrde;

    @Column(name = "ors_identifkey_psem", length = 30)
    private String orsIdentifkeyPsem;

    @Column(name = "ors_semana_inse")
    private Integer orsSemanaInse;

    @Column(name = "ors_fechainicio_inse")
    private LocalDate orsFechainicioInse;

    @Column(name = "ors_fechafin_inse")
    private LocalDate orsFechafinInse;

    @Column(name = "ors_valororden_inse", precision = 17, scale = 2)
    private BigDecimal orsValorordenInse;

    @Column(name = "ors_programadosemana_inse", precision = 17, scale = 2)
    private BigDecimal orsProgramadosemanaInse;

    @Column(name = "ors_ejecutadosemana_inse", precision = 17, scale = 2)
    private BigDecimal orsEjecutadosemanaInse;

    @Column(name = "ors_programadoacumulado_inse", precision = 17, scale = 2)
    private BigDecimal orsProgramadoacumuladoInse;

    @Column(name = "ors_ejecutadoacumulado_inse", precision = 17, scale = 2)
    private BigDecimal orsEjecutadoacumuladoInse;

    @Column(name = "ors_cantidadprogramadasem_inse", precision = 17, scale = 2)
    private BigDecimal orsCantidadprogramadasemInse;

    @Column(name = "ors_cantidadejecutadasem_inse", precision = 17, scale = 2)
    private BigDecimal orsCantidadejecutadasemInse;

    @Column(name = "ors_cantidadprogramadaacu_inse", precision = 17, scale = 2)
    private BigDecimal orsCantidadprogramadaacuInse;

    @Column(name = "ors_cantidadejecutadaacu_inse", precision = 17, scale = 2)
    private BigDecimal orsCantidadejecutadaacuInse;

    @Column(name = "ors_poravancefisico_inse", precision = 8, scale = 2)
    private BigDecimal orsPoravancefisicoInse;

    @Column(name = "ors_poravancefinanciero_inse", precision = 8, scale = 2)
    private BigDecimal orsPoravancefinancieroInse;

    @Column(name = "ors_atrasoadelanto_inse", precision = 17, scale = 2)
    private BigDecimal orsAtrasoadelantoInse;

    @Column(name = "ors_estadoavance_inse", length = 30)
    private String orsEstadoavanceInse;

    @Column(name = "ors_observacion_inse", columnDefinition = "TEXT")
    private String orsObservacionInse;

    @Column(name = "ors_fechasistema_inse")
    private LocalDateTime orsFechasistemaInse;

    @Column(name = "ors_tiporegist_inse", length = 2)
    private String orsTiporegistInse;

    @Column(name = "ors_estadoreg_inse", length = 1)
    private String orsEstadoregInse;
}