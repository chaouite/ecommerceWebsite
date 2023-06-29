package com.swt.fahrradshop.bestellung.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@AllArgsConstructor
public class WarenkorbUnorderedEvent {
    private String warenkorbId;
}
