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
@AllArgsConstructor
@NoArgsConstructor
// this event will notify that a ProduktCreateCommand is received
public class ProduktCreatedEvent {
    @TargetAggregateIdentifier
    private String produktId;
    private String Name;
    private BigDecimal Preis;
    private BigDecimal Anzahl;
    private Kategorie Kategorie;
    private Verfuegbarkeit Verfuegbarkeit;
    private BigDecimal AnzahlToReserve;


}