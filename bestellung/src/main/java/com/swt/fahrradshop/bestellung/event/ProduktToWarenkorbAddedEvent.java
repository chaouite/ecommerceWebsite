package com.swt.fahrradshop.bestellung.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduktToWarenkorbAddedEvent {
    private String warenkorbId;
    private String produktId;
    //number of Produkt to be added
    private Integer anzahl;

}
