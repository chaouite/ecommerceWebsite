package com.swt.fahrradshop.bestellung.command;

import com.swt.fahrradshop.bestellung.valueObject.BestellungsstatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class CreateBestellungCommand {
    @TargetAggregateIdentifier
    private final String bestellungId;

    private final BestellungsstatusEnum bestellungsstatus;
    private final String kundeId;
    private final String warenkorbId;
    private final BigDecimal gesamtpreis;

}
