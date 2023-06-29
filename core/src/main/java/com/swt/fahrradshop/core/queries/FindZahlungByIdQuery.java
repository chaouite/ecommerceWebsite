package com.swt.fahrradshop.core.queries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindZahlungByIdQuery {
    private String zahlungId;
}
