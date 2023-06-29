package com.swt.fahrradshop.bestellung.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class UnorderWarenkorbCommand {
    @TargetAggregateIdentifier
    private final String warenkorbId;
}

