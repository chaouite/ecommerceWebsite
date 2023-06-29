package com.swt.fahrradshop.bestellung.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class BestellungCommandModel {

    private String kundeId;
    private String warenkorbId;
    private BigDecimal gesamtpreis;
}
