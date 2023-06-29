package com.swt.fahrradshop.bestellung.projection;

import com.swt.fahrradshop.bestellung.entity.BestellungEntity;
import com.swt.fahrradshop.bestellung.event.BestellungCanceledEvent;
import com.swt.fahrradshop.bestellung.event.BestellungCreatedEvent;
import com.swt.fahrradshop.bestellung.event.PayedOrSentBestellungUpdatedEvent;
import com.swt.fahrradshop.bestellung.repository.BestellungRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BestellungProjection {

    private final BestellungRepository bestellungRepository;

    public BestellungProjection(BestellungRepository bestellungRepository) {
        this.bestellungRepository = bestellungRepository;
    }

    @EventHandler
    public void on(BestellungCreatedEvent evt) {

        BestellungEntity bestellung = new BestellungEntity(
                evt.getBestellungId(),
                evt.getBestellungsstatus().toString(),
                evt.getKundeId(),
                evt.getWarenkorbId(),
                evt.getGesamtpreis()
        );
        bestellungRepository.save(bestellung);
    }

    @EventHandler
    public void on(BestellungCanceledEvent evt) {
        bestellungRepository.deleteById(evt.getBestellungId());
    }

    @EventHandler
    public void on(PayedOrSentBestellungUpdatedEvent evt) {
        BestellungEntity bestellung = bestellungRepository.findByBestellungId(evt.getBestellungId());
        bestellung.setBestellungsstatus(evt.getBestellungsstatusEnum().toString());
    }

}
