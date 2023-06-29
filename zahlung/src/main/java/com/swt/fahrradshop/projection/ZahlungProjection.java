package com.swt.fahrradshop.projection;

import com.swt.fahrradshop.core.events.ZahlungCanceledEvent;
import com.swt.fahrradshop.core.valueObject.ZahlungsstatusEnum;
import com.swt.fahrradshop.entity.ZahlungEntity;
import com.swt.fahrradshop.repository.ZahlungRepository;
import com.swt.fahrradshop.core.events.ZahlungProcessedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZahlungProjection {

    private ZahlungRepository zahlungRepository;

    @Autowired
    public ZahlungProjection(ZahlungRepository zahlungRepository) {
        this.zahlungRepository = zahlungRepository;
    }

    @EventHandler
    public void on(ZahlungProcessedEvent evt) {
        zahlungRepository.save(new ZahlungEntity(
                        evt.getZahlungId(),
                        evt.getBestellungId(),
                        evt.getGesamtpreis(),
                        evt.getKreditKarte(),
                        evt.getZahlungsstatusEnum().toString()
                )
        );
    }

    @EventHandler
    public void on(ZahlungCanceledEvent evt) {
        zahlungRepository.deleteById(evt.getZahlungId());
    }


}
