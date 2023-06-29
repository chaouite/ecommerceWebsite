package com.swt.fahrradshop.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.swt.fahrradshop.core.valueObject.KreditKarte;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "zahlungen")
public class ZahlungEntity {
    @Id
    private String zahlungId;

    private String bestellungId;
    private BigDecimal gesamtpreis;

    @Embedded
    private KreditKarte kreditKarte;
    private String zahlungsstatus;
}
