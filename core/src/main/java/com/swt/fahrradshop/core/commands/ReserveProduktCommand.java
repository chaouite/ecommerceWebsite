package com.swt.fahrradshop.core.commands;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReserveProduktCommand {
    private String produktId;
    private Integer AnzahlToReserve;

    
}
