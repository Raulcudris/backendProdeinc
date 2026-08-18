package com.system.crosscutting.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orsconfnovedadtipos")
public class EntyOrsconfnovedadtipos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ors_primarykey_novt")
    private Integer orsPrimarykeyNovt;

    @Column(name = "ors_tiponovedad_novt", length = 40, nullable = false)
    private String orsTiponovedadNovt;

    @Column(name = "ors_descnovedad_novt", length = 150)
    private String orsDescnovedadNovt;

    @Column(name = "ors_estadoreg_novt", length = 1)
    private String orsEstadoregNovt;
}