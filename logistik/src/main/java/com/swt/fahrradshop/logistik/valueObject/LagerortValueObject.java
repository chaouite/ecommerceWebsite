package com.swt.fahrradshop.logistik.valueObject;

//TODO: import und List Produkt anpassen
//import com.swt.fahrradshop.produkt

import javax.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class LagerortValueObject {
    
    private String lagerortId; 
    private String standort;
   // private List<Produkt> lagerbestand; 
}
