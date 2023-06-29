package com.swt.fahrradshop.bestellung.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarenkorbOrderedEvent {
    private String warenkorbId;
}
