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
public class LogistikCreatedEvent {
    @TargetAggregateIdentifier
    private String logistikId;
    private String bestellungId;
    private LieferstatusEnum lieferstatus;
// private LagerortValueObject lagerortValueObject;;
//  private VersandnummerValueObject versandnummerValueObject;
//  private LieferadresseValueObject lieferadresseValueObject;
}
