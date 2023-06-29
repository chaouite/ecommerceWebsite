package com.swt.fahrradshop.core.commands;

import com.swt.fahrradshop.core.valueObject.LieferstatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
@Data
@Builder
@AllArgsConstructor
public class CreateLogistikCommand {
    @TargetAggregateIdentifier
    private final String logistikId;
    private final String bestellungId;
    private final LieferstatusEnum lieferstatusEnum;

}
