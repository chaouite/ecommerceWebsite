package com.swt.fahrradshop.bestellung.command;

import com.swt.fahrradshop.bestellung.valueObject.WarenkorbProdukt;
import com.swt.fahrradshop.bestellung.valueObject.WarenkorbStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class CreateWarenkorbCommand {
    @TargetAggregateIdentifier
    private final String warenkorbId;

    private final String KundeId;
    //empty list of products while creating the Warenkorb
    private final List<WarenkorbProdukt> produkte;
    private final WarenkorbStatusEnum warenkorbStatus;

}
