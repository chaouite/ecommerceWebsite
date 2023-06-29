package com.swt.fahrradshop.bestellung.valueObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class WarenkorbProdukt {

    private String produktId;
    private Integer produktAnzahl;
}
