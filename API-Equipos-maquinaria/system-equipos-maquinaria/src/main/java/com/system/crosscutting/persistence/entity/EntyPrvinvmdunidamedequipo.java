package com.system.crosscutting.persistence.entity;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "prvinvmdunidamedequipo")
public class EntyPrvinvmdunidamedequipo {

    @Id
    @Column(name = "prv_tipunidamed_unme", nullable = false, length = 30)
    private String prvTipunidamedUnme;

    @Column(name = "prv_descmedida_unme", length = 40)
    private String prvDescmedidaUnme;

    @Column(name = "prv_estadoreg_unme", length = 2)
    private String prvEstadoregUnme;
}