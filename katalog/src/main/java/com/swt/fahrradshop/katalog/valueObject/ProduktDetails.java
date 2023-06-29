package com.swt.fahrradshop.katalog.valueObject;

import com.swt.fahrradshop.katalog.entity.Produkt;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
public class ProduktDetails extends Produkt {

    private String produktId;
    private BigDecimal Anzahl;

}
