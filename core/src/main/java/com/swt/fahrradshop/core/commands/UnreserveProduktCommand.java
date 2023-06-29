package com.swt.fahrradshop.core.commands;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UnreserveProduktCommand {
    private String produktId;
    private Integer AnzahlToReserve;
}
