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
 * Entidad JPA que representa la tabla ORSORDMDPROYECSEMANA.
 *
 * Esta tabla almacena la proyección semanal de trabajo
 * asociada a una orden de servicio.
 */
@Getter
@Setter
@Entity
@Table(name = "ORSORDMDPROYECSEMANA")
public class EntyOrsordmdproyecsemana implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Código secuencial autoincremental generado por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORS_PRIMARYKEY_PSEM")
    private Integer orsPrimarykeyPsem;

    /**
     * Código único funcional de la proyección semanal.
     */
    @Column(name = "ORS_IDENTIFKEY_PSEM", length = 30, nullable = false)
    private String orsIdentifkeyPsem;

    /**
     * Código único de la orden de servicio.
     */
    @Column(name = "ORS_IDENTIFKEY_ORDE", length = 30)
    private String orsIdentifkeyOrde;

    /**
     * Número secuencial de la semana.
     */
    @Column(name = "ORS_NUMEROSEM_PSEM")
    private Integer orsNumerosemPsem;

    /**
     * Título descriptivo de la semana.
     */
    @Column(name = "ORS_TITULOSEM_PSEM", length = 60)
    private String orsTitulosemPsem;

    /**
     * Fecha de inicio de la semana.
     */
    @Column(name = "ORS_SEMFECHINI_PSEM")
    private LocalDate orsSemfechiniPsem;

    /**
     * Fecha de finalización de la semana.
     */
    @Column(name = "ORS_SEMFECHFIN_PSEM")
    private LocalDate orsSemfechfinPsem;

    /**
     * Días hábiles disponibles para trabajar.
     */
    @Column(name = "ORS_DIASHABILES_PSEM", length = 80)
    private String orsDiashabilesPsem;

    /**
     * Días no hábiles o no disponibles.
     */
    @Column(name = "ORS_DIASNHABILES_PSEM", length = 80)
    private String orsDiasnhabilesPsem;

    /**
     * Tipo de registro: 1=Registro original, 2=Novedad.
     */
    @Column(name = "ORS_TIPOREGIST_PSEM", length = 2)
    private String orsTiporegistPsem;

    /**
     * Estado del registro: 1=Abierto, 2=Cerrado, 3=Cancelado.
     */
    @Column(name = "ORS_ESTADOREG_PSEM", length = 1)
    private String orsEstadoregPsem;
}