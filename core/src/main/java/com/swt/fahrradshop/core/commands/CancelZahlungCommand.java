package com.swt.fahrradshop.core.commands;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@AllArgsConstructor
public class CancelZahlungCommand {
    @TargetAggregateIdentifier
    private String zahlungId;
}
