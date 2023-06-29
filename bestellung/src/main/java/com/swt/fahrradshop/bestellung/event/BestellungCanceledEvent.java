package com.swt.fahrradshop.bestellung.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BestellungCanceledEvent {
    private String bestellungId;
}
