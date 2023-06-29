package com.swt.fahrradshop.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.swt.fahrradshop.core.valueObject.KreditKarte;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ZahlungQueryModel {
    private String zahlungId;
    private String bestellungId;
    private BigDecimal gesamtpreis;
    private KreditKarte kreditKarte;
    private String zahlungsstatusEnum;
}
