package com.system.crosscutting.persistence.entity;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rechomeestadist")
public class EntyRechomeestadist implements Serializable {

    private static final long serialVersionUID = 1L;
    /** Codigo secuencial autoincremental generado por la base de datos*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rec_identifkey_rhes")
    private Integer recIdentifkeyRhes;

    /**Tipo registro al cual pertenece la estadistica: "1"=Usuario o Cliente/"2"=Prestador o Homepage/"3"=Oferta Publicada */
    @Basic(optional = false)
    @Column(name = "rec_typeregis_rhes")
    private String recTyperegisRhes;

    /** Codigo Cliente/Prestador/Oferta Publicada - a quien pertenece el registro  */
    @Basic(optional = false)
    @Column(name = "rec_idenumkey_rhes")
    private String recIdenumkeyRhes;

    /** Llave tipo de registro estadistico: ejemplo "CONTRACTS-COMPLIANT"=Total contratos finalizados conforme por la contraparte */
    @Basic(optional = false)
    @Column(name = "rec_keylocate_rhes")
    private String recKeylocateRhes;

    /** Nota textual o concepto emitido como calificacion a una cliente o prestador de servicios */
    @Basic(optional = false)
    @Column(name = "rec_notdescrip_rhes")
    private String recNotdescripRhes;
    
    /** Fecha y hora actualización del registro */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "rec_dateregist_rhes")
    private LocalDateTime recDateregistRhes;

    /** Contador de registros estadisticos */
    @Basic(optional = false)
    @Column(name = "rec_regcount_rhes")
    private Float recRegcountRhes;

    /** Estado del registro: 1= Activo 2= Inactivo */
    @Basic(optional = false)
    @Column(name = "rec_statusregi_rhes")
    private String recStatusregiRhes;
}
