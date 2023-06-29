package com.swt.fahrradshop.katalog.entity;

import com.swt.fahrradshop.katalog.valueObject.Kategorie;
import com.swt.fahrradshop.katalog.valueObject.Verfuegbarkeit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="PRODUKTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produkt {
    @Id
    @Column(name = "PRODUKT_ID")
    private String id;
    @Column(name = "NAME")
    private String Name;
    @Column(name = "PREIS")
    private BigDecimal Preis;
    @Column(name = "ANZAHL")
    private BigDecimal Anzahl;
    @Column(name = "KATEGORIE")
    // this will heplp to display the enum as a string in the DB
    @Enumerated(EnumType.STRING)
    private Kategorie Kategorie;
    @Column(name = "VERFUEGBARKEIT")
    @Enumerated(EnumType.STRING)
    private Verfuegbarkeit Verfuegbarkeit;
    @Column (name ="ANZAHL_TO_RESERVE")
    private BigDecimal AnzahlToReserve;




}

