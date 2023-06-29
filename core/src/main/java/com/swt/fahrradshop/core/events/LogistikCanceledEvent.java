package com.swt.fahrradshop.core.events;

import com.swt.fahrradshop.core.valueObject.LieferstatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogistikCanceledEvent {
    @TargetAggregateIdentifier
    private String logistikId;

}
