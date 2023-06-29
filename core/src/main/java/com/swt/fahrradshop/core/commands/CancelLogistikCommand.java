package com.swt.fahrradshop.core.commands;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.swt.fahrradshop.core.valueObject.LieferstatusEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class CancelLogistikCommand {
    @TargetAggregateIdentifier
    private  final String logistikId;

}
