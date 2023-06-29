package com.swt.fahrradshop.bestellung.model;

import com.swt.fahrradshop.bestellung.valueObject.WarenkorbProdukt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarenkorbQueryModel {

    private String warenkorbId;
    private String KundeId;
    private List<WarenkorbProdukt> produkteList;
    private String warenkorbStatus;
}

