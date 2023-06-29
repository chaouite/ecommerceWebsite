package com.swt.fahrradshop.core.valueObject;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KreditKarte {
    private String number;
    private String holder;
    private String expDate;
    private String cvv;
}
