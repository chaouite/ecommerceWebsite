package com.swt.fahrradshop.bestellung.event;

import com.swt.fahrradshop.bestellung.valueObject.WarenkorbProdukt;
import com.swt.fahrradshop.bestellung.valueObject.WarenkorbStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarenkorbCreatedEvent {
    private String warenkorbId;
    private String KundeId;
    private List<WarenkorbProdukt> produkte;
    private WarenkorbStatusEnum warenkorbStatus;
}
