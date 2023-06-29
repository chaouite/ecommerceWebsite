package com.swt.fahrradshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.swt.fahrradshop.core.valueObject.KreditKarte;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZahlungCommandModel {


    private String bestellungId;
    private BigDecimal gesamtpreis;
    private KreditKarte kreditKarte;
}
