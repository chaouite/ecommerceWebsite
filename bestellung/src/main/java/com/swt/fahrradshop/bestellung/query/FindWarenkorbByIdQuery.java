package com.swt.fahrradshop.bestellung.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindWarenkorbByIdQuery {
    private String warenkorbId;
}
