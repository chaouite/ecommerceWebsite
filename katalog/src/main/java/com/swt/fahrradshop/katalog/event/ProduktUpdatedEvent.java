package com.swt.fahrradshop.katalog.event;

import com.swt.fahrradshop.katalog.valueObject.Kategorie;
import com.swt.fahrradshop.katalog.valueObject.Verfuegbarkeit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduktUpdatedEvent {

    @TargetAggregateIdentifier
    private String produktId;
    private String newName;
    private BigDecimal newPreis;
    private BigDecimal newAnzahl;
    private Kategorie newKategorie;
    private Verfuegbarkeit newVerfuegbarkeit;
    private BigDecimal newAnzahlToReserve;
}
