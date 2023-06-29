package com.swt.fahrradshop.katalog.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@AllArgsConstructor
@Builder
public class DeleteProduktCommand {
    private String produktId;
}
