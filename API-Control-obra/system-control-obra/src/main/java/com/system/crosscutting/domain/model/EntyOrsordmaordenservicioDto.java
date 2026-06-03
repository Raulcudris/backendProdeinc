package com.system.crosscutting.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO que representa la información de la tabla ORSORDMAORDENSERVICIO.
 *
 * Se mantienen los mismos nombres de atributos de la entidad para permitir
 * la conversión automática mediante GsonUtil y el patrón Translator.
 */
@Getter
@Setter
public class EntyOrsordmaordenservicioDto {

    /**
     * Código secuencial autoincremental generado por la base de datos.
     */
    private Integer orsPrimarykeyOrde;

    /**
     * Código único funcional de la orden de servicio.
     */
    private String orsIdentifkeyOrde;

    /**
     * Fecha de expedición de la autorización del servicio.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsAutorifechaOrde;

    /**
     * Código del servicio básico autorizado.
     */
    private String orsCodservicioSebs;

    /**
     * Evento que originó la prestación del servicio.
     */
    private String orsServiceventOrde;

    /**
     * Lugar donde se realizará la prestación del servicio.
     */
    private String orsServiclugarOrde;

    /**
     * Objeto del contrato o descripción amplia del servicio.
     */
    private String orsServicobjetoOrde;

    /**
     * Fecha de inicio del plan de trabajo.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsPlanfechiniOrde;

    /**
     * Fecha de finalización del plan de trabajo.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsPlanfechfinOrde;

    /**
     * Código único del proveedor autorizado.
     */
    private String prvIdentifkeyMprv;

    /**
     * Código único del representante legal.
     */
    private String prvIdentifkeyRelg;

    /**
     * Código del tipo de valor autorizado.
     */
    private String ordTipovalorTiva;

    /**
     * Valor total del servicio autorizado.
     */
    private BigDecimal orsValorbaseOrde;

    /**
     * Valor total abonado.
     */
    private BigDecimal carValaboCamg;

    /**
     * Valor saldo pendiente.
     */
    private BigDecimal carValsalCamg;

    /**
     * Estado de la orden: 1=Abierto, 2=Cerrado, 3=Cancelado.
     */
    private String orsEstadoregOrde;
}