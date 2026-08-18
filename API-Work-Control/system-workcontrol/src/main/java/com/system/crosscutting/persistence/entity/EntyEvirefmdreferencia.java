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
@Table(name = "evirefmdreferencia")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntyEvirefmdreferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evi_primarykey_refe")
    private Integer eviPrimarykeyRefe;

    @Column(name = "evi_identifkey_refe", nullable = false, length = 30)
    private String eviIdentifkeyRefe;

    @Column(name = "evi_identifkey_evid", nullable = false, length = 30)
    private String eviIdentifkeyEvid;

    @Column(name = "evi_tiporegistro_refe", nullable = false, length = 50)
    private String eviTiporegistroRefe;

    @Column(name = "evi_identifregistro_refe", nullable = false, length = 50)
    private String eviIdentifregistroRefe;

    @Column(name = "evi_observacion_refe", length = 500)
    private String eviObservacionRefe;

    @Column(name = "evi_tiporegist_refe", length = 2)
    private String eviTiporegistRefe;

    @Column(name = "evi_estadoreg_refe", length = 2)
    private String eviEstadoregRefe;
}