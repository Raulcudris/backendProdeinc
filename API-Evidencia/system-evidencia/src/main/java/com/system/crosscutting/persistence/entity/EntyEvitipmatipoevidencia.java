package com.system.crosscutting.persistence.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "evitipmatipoevidencia")
public class EntyEvitipmatipoevidencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evi_primarykey_tiev")
    private Integer eviPrimarykeyTiev;

    @Column(name = "evi_identifkey_tiev", nullable = false, unique = true, length = 30)
    private String eviIdentifkeyTiev;

    @Column(name = "evi_descripcion_tiev", nullable = false, length = 150)
    private String eviDescripcionTiev;

    @Column(name = "evi_tiporegist_tiev", length = 2)
    private String eviTiporegistTiev;

    @Column(name = "evi_estadoreg_tiev", length = 2)
    private String eviEstadoregTiev;
}