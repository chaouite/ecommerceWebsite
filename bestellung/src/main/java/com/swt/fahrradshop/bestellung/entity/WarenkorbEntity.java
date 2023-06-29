package com.swt.fahrradshop.bestellung.entity;

import com.swt.fahrradshop.bestellung.valueObject.WarenkorbProdukt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "warenkoorbe")
public class WarenkorbEntity {
    @Id
    private String warenkorbId;

    private String kundeId;

    @ElementCollection
    @CollectionTable(name = "warenkorb_produkte", joinColumns = @JoinColumn(name = "warenkorbId"))
    private List<WarenkorbProdukt> produkteList;

    private String warenkorbStatus;

}
