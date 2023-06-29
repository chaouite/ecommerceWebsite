package com.swt.fahrradshop.bestellung.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bestellungen")
public class BestellungEntity {

    @Id
    private String bestellungId;

    private String bestellungsstatus;
    private String kundeId;
    private String warenkorbId;
    private BigDecimal gesamtpreis;
}
