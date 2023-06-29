package com.swt.fahrradshop.bestellung.event;

import com.swt.fahrradshop.bestellung.valueObject.BestellungsstatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PayedOrSentBestellungUpdatedEvent {
    private String bestellungId;
    private final BestellungsstatusEnum bestellungsstatusEnum;
}
