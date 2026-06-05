package com.system.crosscutting.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * Entidad JPA que representa la tabla DOCVENMDVENCIMIENTO.
 *
 * Esta tabla almacena el control de vencimientos y renovaciones de documentos
 * legales, técnicos, contractuales, administrativos o de maquinaria.
 */
@Getter
@Setter
@Entity
@Table(name = "DOCVENMDVENCIMIENTO")
public class EntyDocvenmdvencimiento {

    /**
     * Código secuencial autoincremental del vencimiento documental.
     */
    @Id
    @Column(name = "DOC_PRIMARYKEY_VEDO")
    private Integer docPrimarykeyVedo;

    /**
     * Código único funcional del vencimiento documental.
     */
    @Column(name = "DOC_IDENTIFKEY_VEDO", length = 30, nullable = false)
    private String docIdentifkeyVedo;

    /**
     * Código único funcional del documento asociado.
     */
    @Column(name = "DOC_IDENTIFKEY_DOCU", length = 30, nullable = false)
    private String docIdentifkeyDocu;

    /**
     * Fecha de vencimiento registrada.
     */
    @Column(name = "DOC_FECHAVENCE_VEDO", nullable = false)
    private LocalDate docFechavenceVedo;

    /**
     * Días antes del vencimiento para generar alerta.
     */
    @Column(name = "DOC_DIASALERTA_VEDO", nullable = false)
    private Integer docDiasalertaVedo;

    /**
     * Estado del vencimiento: 1=Vigente, 2=Próximo, 3=Vencido, 4=Renovado.
     */
    @Column(name = "DOC_ESTADOVENC_VEDO", length = 1, nullable = false)
    private String docEstadovencVedo;

    /**
     * Fecha de renovación del documento.
     */
    @Column(name = "DOC_FECHARENOVA_VEDO")
    private LocalDate docFecharenovaVedo;

    /**
     * Observaciones del vencimiento documental.
     */
    @Column(name = "DOC_OBSERVACION_VEDO", columnDefinition = "TEXT")
    private String docObservacionVedo;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    @Column(name = "DOC_ESTADOREG_VEDO", length = 1, nullable = false)
    private String docEstadoregVedo;
}