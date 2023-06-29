package com.swt.fahrradshop.core.events;

import com.swt.fahrradshop.core.valueObject.LieferstatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ShippingSentEvent {
    private String logistikId;
    private String bestellungId;
    private LieferstatusEnum lieferstatusEnum;
}
