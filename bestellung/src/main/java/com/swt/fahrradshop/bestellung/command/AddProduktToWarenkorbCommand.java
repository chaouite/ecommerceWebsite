package com.swt.fahrradshop.bestellung.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class AddProduktToWarenkorbCommand {
    @TargetAggregateIdentifier
    private final String warenkorbId;
    private final String produktId;
    //number of Produkt to be added
    private final Integer anzahl;

}
