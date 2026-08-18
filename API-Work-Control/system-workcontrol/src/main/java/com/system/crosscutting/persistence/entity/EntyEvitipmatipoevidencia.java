package com.system.crosscutting.persistence.entity;

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

@Entity
@Table(name = "evitipmatipoevidencia")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntyEvitipmatipoevidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evi_primarykey_tiev")
    private Integer eviPrimarykeyTiev;

    @Column(name = "evi_identifkey_tiev", nullable = false, length = 30)
    private String eviIdentifkeyTiev;

    @Column(name = "evi_descripcion_tiev", nullable = false, length = 150)
    private String eviDescripcionTiev;

    @Column(name = "evi_tiporegist_tiev", length = 2)
    private String eviTiporegistTiev;

    @Column(name = "evi_estadoreg_tiev", length = 2)
    private String eviEstadoregTiev;
}