package com.swt.fahrradshop.katalog.dto;

import com.swt.fahrradshop.katalog.valueObject.Kategorie;
import com.swt.fahrradshop.katalog.valueObject.Verfuegbarkeit;
import lombok.Value;

import java.math.BigDecimal;
@Value

public class ProduktDto {

    private String Name;
    private BigDecimal Preis;
    private BigDecimal Anzahl;
    private Kategorie Kategorie;
    private Verfuegbarkeit Verfuegbarkeit;
    private BigDecimal AnzahlToReserve;
}
