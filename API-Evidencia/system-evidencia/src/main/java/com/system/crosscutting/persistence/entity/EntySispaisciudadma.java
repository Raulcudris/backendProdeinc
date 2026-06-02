package com.system.crosscutting.persistence.entity;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sispaisciudadma")
public class EntySispaisciudadma implements Serializable
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "sis_codmun_simu")
    private String  sisCodmunSimu;

    /** Código del municipio dentro de cada pais, es un codigo interno de gestion auxiliar */
    @Basic(optional = false)
    @Column(name = "sis_idemun_simu")
    private String  sisIdemunSimu;

    /** Código secuencial unico del departamento o estado (codigo unico pais + codigo unico departamento) */
    @Basic(optional = false)
    @Column(name = "sis_idedpt_sidp")
    private String  sisIdedptSidp;

    /** Código secuencial unico del pais según codificaicon internacional */
    @Basic(optional = false)
    @Column(name = "sis_codpai_sipa")
    private String  sisCodpaiSipa;

    /** Nombre ciudad */
    @Basic(optional = false)
    @Column(name = "sis_nombre_simu")
    private String  sisNombreSimu;

    /** Estado del registro: 1= Activo 2= Inactivo */
    @Basic(optional = false)
    @Column(name = "sis_estreg_simu")
    private String  sisEstregSimu;
}
