package com.swt.fahrradshop.logistik.valueObject;

import javax.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class VersandnummerValueObject {
    private String trackingNummer;
}
