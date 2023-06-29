package com.swt.fahrradshop.katalog.command;

import com.swt.fahrradshop.katalog.valueObject.Kategorie;
import com.swt.fahrradshop.katalog.valueObject.Verfuegbarkeit;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UpdateProduktCommand {
    @TargetAggregateIdentifier
    private String produktId;
    private String newName;
    private BigDecimal newPreis;
    private BigDecimal newAnzahl;
    private Kategorie newKategorie;
    private Verfuegbarkeit newVerfuegbarkeit;
    private BigDecimal newAnzahlToReserve;

}
