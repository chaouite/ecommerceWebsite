package com.swt.fahrradshop.bestellung.projection;

import com.swt.fahrradshop.bestellung.entity.WarenkorbEntity;
import com.swt.fahrradshop.bestellung.event.*;
import com.swt.fahrradshop.bestellung.repository.WarenkorbRepository;
import com.swt.fahrradshop.bestellung.valueObject.WarenkorbProdukt;
import com.swt.fahrradshop.bestellung.valueObject.WarenkorbStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class WarenkorbProjection {

    private final WarenkorbRepository warenkorbRepository;

    public WarenkorbProjection(WarenkorbRepository warenkorbRepository) {
        this.warenkorbRepository = warenkorbRepository;
    }

    @EventHandler
    public void on(WarenkorbCreatedEvent evt) {
        WarenkorbEntity warenkorb = new WarenkorbEntity(
                evt.getWarenkorbId(),
                evt.getKundeId(),
                evt.getProdukte(),
                evt.getWarenkorbStatus().toString()
        );
        warenkorbRepository.save(warenkorb);
    }

    @EventHandler
    public void on(ProduktToWarenkorbAddedEvent evt) {
        //get the warenkorb from DB
        WarenkorbEntity warenkorbInDB = warenkorbRepository.findByWarenkorbId(evt.getWarenkorbId());
        //get the list of products of that warenkorb
        List<WarenkorbProdukt> produkteInWarenkorbListe = warenkorbInDB.getProdukteList();
        Integer anzahlToBeAdded = evt.getAnzahl();
        boolean found = false;
        for (WarenkorbProdukt produkt : produkteInWarenkorbListe) {
            if (produkt.getProduktId().equals(evt.getProduktId())) {
                anzahlToBeAdded = anzahlToBeAdded + produkt.getProduktAnzahl();
                produkt.setProduktAnzahl(anzahlToBeAdded);
                found = true;
            }
        }
        if (found) {
            warenkorbInDB.setProdukteList(produkteInWarenkorbListe);
        } else {
            WarenkorbProdukt produktToAdd = new WarenkorbProdukt(evt.getProduktId(), evt.getAnzahl());
            warenkorbInDB.getProdukteList().add(produktToAdd);
            warenkorbRepository.save(new WarenkorbEntity(
                    warenkorbInDB.getWarenkorbId(),
                    warenkorbInDB.getKundeId(),
                    warenkorbInDB.getProdukteList(),
                    warenkorbInDB.getWarenkorbStatus()));
        }
        warenkorbInDB.setProdukteList(produkteInWarenkorbListe);
        warenkorbRepository.save(warenkorbInDB);
    }


    @EventHandler
    public void on(ProduktFromWarenkorbDeletedEvent evt) {

        //get the warenkorb from DB
        WarenkorbEntity warenkorbInDB = warenkorbRepository.findByWarenkorbId(evt.getWarenkorbId());
        //get the list of products of that warenkorb
        List<WarenkorbProdukt> produkteInWarenkorbListe = warenkorbInDB.getProdukteList();

        WarenkorbProdukt produktToRemove = null;

        for (WarenkorbProdukt produkt : produkteInWarenkorbListe) {
            if (produkt.getProduktId().equals(evt.getProduktId())) {
                if (produkt.getProduktAnzahl() > 1) {
                    Integer newAnzahl = produkt.getProduktAnzahl() - 1;
                    produkt.setProduktAnzahl(newAnzahl);
                } else {//when only one Produkt is left
                    produktToRemove = produkt;
                }
            }
        }
        if (produktToRemove != null) {
            produkteInWarenkorbListe.remove(produktToRemove);
        }
        warenkorbInDB.setProdukteList(produkteInWarenkorbListe);
        warenkorbRepository.save(warenkorbInDB);
    }

    @EventHandler
    public void on(WarenkorbOrderedEvent evt) {
        WarenkorbEntity warenkorbInDb = warenkorbRepository.findByWarenkorbId(evt.getWarenkorbId());
        if (warenkorbInDb.getProdukteList().size() == 0) {
            log.warn("An empty Warenkorb can not be ordered!!");
        } else {
            warenkorbInDb.setWarenkorbStatus(WarenkorbStatusEnum.BESTELLT.toString());
        }
        warenkorbRepository.save(warenkorbInDb);
    }

    @EventHandler
    public void on(WarenkorbUnorderedEvent evt) {
        WarenkorbEntity warenkorbInDb = warenkorbRepository.findByWarenkorbId(evt.getWarenkorbId());
        warenkorbInDb.setWarenkorbStatus(WarenkorbStatusEnum.NICHT_BESTELLT.toString());
        warenkorbRepository.save(warenkorbInDb);
    }
}
